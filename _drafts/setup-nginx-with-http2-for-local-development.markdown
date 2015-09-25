---
layout: post
comments: true
date: 2015-09-24 16:54:00+0200
authors: Gregers Rygg
title: "Setup nginx with HTTP/2 for local development (OS X)"
description: "Simple instructions on how to get started with nginx on your local developement computer"
tags:
- nginx
- http/2
- https
---
HTTP/2 became an official standard in May earlier this year, and support is starting to land in servers already. The most recent, and very welcome addition, is nginx 1.9.5 [[1]](https://www.nginx.com/blog/nginx-1-9-5/). What makes nginx extra awesome is that it's very easy to set up in front of any other HTTP 1.x server (or HTTP/2 for that matter). Server push won't work just yet, but at least we can start to test how multiplexing works. Multiplexing is said to eliminate the need to concatenate resources into bundles. With multiple releases a day, it just feels wrong that clients have to download the whole 100+ kB JavaScript bundle after every release. If we don't need to bundle anymore, the clients only need to download the resources that had changed since their last visit!

[HTTPS over TLS 1.2 is a requirement to use HTTP/2](https://http2.github.io/http2-spec/#TLSUsage). [Service Workers also require secure connections](http://www.w3.org/TR/service-workers/#security-considerations), and probably [other features soon](https://w3c.github.io/webappsec/specs/powerfulfeatures/).

This guide will help you set up nginx for local development on OS X, with [proxy passing](https://www.nginx.com/resources/admin-guide/reverse-proxy/) requests to your local server on port 8080 (or whichever port you prefer). In plain English, that means we put nginx in between your browser and your local development server. Your browser communicates securely over HTTP/2 to nginx, and nginx forwards the requests to your local server over unsecured HTTP/1.1.

## Installing nginx

You have to compile nginx with the `--with-http_v2_module` configuration parameter, but [Homebrew](http://brew.sh/) makes that a breeze. If you don't have Homebrew already, go and install it right now! It's one of my favourite tools on OS X. Run this one-liner in Terminal to install Homebrew: `ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

If you already have Homebrew, remember to `brew update` to get an updated list of packages

To compile and install nginx with http2:
```$ sudo brew install --devel --with-spdy nginx```

After it has been installed, you'll need to edit /usr/local/etc/nginx/nginx.conf
Comment out the existing `server` section, and copy-paste in this instead <sup>[[2]](https://ma.ttias.be/enable-http2-in-nginx/)</sup>:

```
server {
    listen                     443 ssl http2;
    server_name                localhost;

    ssl                        on;
    ssl_protocols              TLSv1 TLSv1.1 TLSv1.2;
    ssl_certificate            cert.pem;
    ssl_certificate_key        cert.key;

    location / {
        proxy_pass          http://localhost:8080;
        proxy_set_header    Host      $host;
        proxy_set_header    X-Real-IP $remote_addr;
        proxy_set_header    X-HTTPS   'True';
    }
}
```

Now we need to generate a self signed certificate. It will give you warnings in the browser, but it works fine for local development. This command will generate the certificate <sup>[[3]](https://ma.ttias.be/how-to-create-a-self-signed-ssl-certificate-with-openssl/)</sup>:

```
$ cd /usr/local/etc/nginx/
$ sudo openssl req -x509 -sha256 -newkey rsa:2048 -keyout cert.key -out cert.pem \
   -days 1024 -nodes -subj '/CN=local.finn.no'
```
When asked for «Common Name», fill in the hostname you use locally. Most FINN.no developers use `local.finn.no`

If you want nginx to start automatically on boot <sup>[[4]](https://superuser.com/questions/304206/how-do-i-start-nginx-on-port-80-at-os-x-login/474286#474286)</sup>:

```$ sudo cp /usr/local/opt/nginx/homebrew.mxcl.nginx.plist /Library/LaunchDaemons/```

Add to ~/.profile for easy aliases <sup>[[4]](https://superuser.com/questions/304206/how-do-i-start-nginx-on-port-80-at-os-x-login/474286#474286)</sup>:
```
alias nginx-start='sudo launchctl load /Library/LaunchDaemons/homebrew.mxcl.nginx.plist'
alias nginx-stop='sudo launchctl unload /Library/LaunchDaemons/homebrew.mxcl.nginx.plist'
alias nginx-restart='nginx-stop && nginx-start'
```

The aliases won't work before you open a new terminal, but you can execute `.profile` in the current shell:
`$ source ~/.profile`

Start nginx:
`$ nginx-start`

Now you should be able to open (https://localhost/)[https://localhost/] and nginx should proxy pass to your local development server. If you get a 502 error, check that your local server is running.

To see that you really are using HTTP/2 in Chrome, you have to open the Network tab in Developer Tools. Right click (or CTRL-click) the column heading above the network requests, then make sure “Protocol” is checked.
![test](/images/2015-09-25-setup-nginx-with-http2-for-local-development/chrome-show-protocol.png)

![test2](/images/2015-09-25-setup-nginx-with-http2-for-local-development/chrome-protocol-column.png)

If you have problems, check the nginx error log: `/usr/local/var/log/nginx/error.log`

Credit:
1. <https://www.nginx.com/blog/nginx-1-9-5/>
2. <https://ma.ttias.be/enable-http2-in-nginx/>
3. <https://ma.ttias.be/how-to-create-a-self-signed-ssl-certificate-with-openssl/>
4. <https://superuser.com/questions/304206/how-do-i-start-nginx-on-port-80-at-os-x-login/474286#474286>
