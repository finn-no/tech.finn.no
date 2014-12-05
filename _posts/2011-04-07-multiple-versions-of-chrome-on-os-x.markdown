---
authors: gregers
comments: true
date: 2011-04-07 13:37:45+00:00
layout: post
title: Multiple versions of Chrome on OS X
redirect_from: /multiple-versions-of-chrome-on-os-x/
tags:
- browsers
- chrome
- front-end
- OS-X
---

As a professional web developer you should use the bleeding version of Google Chrome, so you're prepared what your users will see a few weeks ahead. But the dev version tends to be a bit more buggy (naturally), and you have to test that your page works with both the beta and the stable version. Google Chrome has a command-line parameter to specify another profile, but it's a bit tricky to add command-line parameters to a Mac application. Yesterday I found a [blog-post by Duo Consulting](http://blog.duoconsulting.com/2011/03/13/multiple-profiles-in-google-chrome-for-os-x/). They have made a nice little script that will generate an app with the profile you specify. I modified it a bit to allow running different versions of Google Chrome as well as a profile for each of them.

To make the install process even easier for others I've zipped the generated applications (they just contain a script).
![](http://tech.finn.no/wp-content/uploads/2011/04/Screen-shot-2011-04-07-at-14.35.27-300x92.png)

_Disclaimer: Each version will have it's own profile, so you have to set up each one from scratch with bookmarks, plugins and everything else. As Scott mentions in the comments, you can use the sync feature in Chrome to keep the installations in sync._





  1. First [download each version](http://www.chromium.org/getting-involved/dev-channel) and rename the original Google Chrome apps to "Google Chrome Stable", "Google Chrome Beta" and "Google Chrome Dev" and put them in Applications **under your user folder**.
I.e.: /Users/gregers/Applications/


  2. Unzip [Chrome-Trio.zip](http://tech.finn.no/wp-content/uploads/2011/04/Chrome-Trio.zip) and put the apps in /Applications


  3. Don't select any of them as default browser, since that will use the default profile instead of the custom. Instead start Safari -> Preferences -> General -> Default web browser -> Select... -> Go to /Applications and choose the version of Chrome you want as default :)



If you're interested in the script I used to make the wrapper-apps. You can find it here: [http://pastebin.com/gPSdNi40](http://pastebin.com/gPSdNi40)
