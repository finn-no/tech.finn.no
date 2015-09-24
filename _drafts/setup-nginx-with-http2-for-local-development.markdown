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
HTTP/2 became an official standard in May earlier this year, and support is starting to land in servers already. [HTTPS over TLS 1.2 is a requirement to use HTTP/2](https://http2.github.io/http2-spec/#TLSUsage). [Same with Service Workers](http://www.w3.org/TR/service-workers/#security-considerations), and probably [other features soon](https://w3c.github.io/webappsec/specs/powerfulfeatures/).
The most recent, and very welcome addition, is [nginx 1.9.5](https://www.nginx.com/blog/nginx-1-9-5/). What makes it extra awesome is that it's very easy to set up nginx in front of any other HTTP 1.x server (or HTTP/2 for that matter).

This guide will help you set up nginx for local development on OS X, with [proxy passing](https://www.nginx.com/resources/admin-guide/reverse-proxy/) requests to your local server on port 8080 (or whichever port you prefer). In plain English, that means we put nginx in between your browser and your local development server. Your browser communicates securely over HTTP/2 to nginx, and nginx forwards the requests to your local server over unsecured HTTP/1.1.

## Installing nginx

You have to compile nginx with the `--with-http_v2_module` configuration parameter, but [Homebrew](http://brew.sh/) makes that a breeze. If you don't have Homebrew already, go and install it right now! It's one of my favourite tools on OS X. Run this one-liner in Terminal to install Homebrew: `ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

To compile and install nginx with http2:
```$ brew install --devel --with-spdy nginx```

After it has been installed, you'll need to edit /usr/local/etc/nginx/nginx.conf
Comment out the existing `server` section, and copy-paste in this instead:

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
        proxy_set_header    X-HTTP2   $http2;
    }
}
```

Now we need to generate a self signed certificate. Run this command to generate the certificate:

```
$ cd /usr/local/etc/nginx/
$ openssl req -x509 -sha256 -newkey rsa:2048 -keyout cert.key -out cert.pem \
   -days 1024 -nodes -subj '/CN=local.finn.no'
```
Common Name: local.finn.no

To make nginx start automatically on boot:

```$ sudo cp /usr/local/opt/nginx/homebrew.mxcl.nginx.plist /Library/LaunchDaemons/```

Add to ~/.profile for easy aliases:
```
alias nginx-start='sudo launchctl load /Library/LaunchDaemons/homebrew.mxcl.nginx.plist'
alias nginx-stop='sudo launchctl unload /Library/LaunchDaemons/homebrew.mxcl.nginx.plist'
alias nginx-restart='nginx-stop && nginx-start'
```

source ~/.profile

Credit:
https://www.nginx.com/blog/nginx-1-9-5/
https://ma.ttias.be/enable-http2-in-nginx/
https://ma.ttias.be/how-to-create-a-self-signed-ssl-certificate-with-openssl/
https://superuser.com/questions/304206/how-do-i-start-nginx-on-port-80-at-os-x-login/474286#474286
