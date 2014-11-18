---
authors:
- espen
comments: true
date: 2013-11-29 09:03:00+00:00
layout: post
slug: low-fi-coffe-surveilence
title: Low-fi Coffe Surveilence
wordpress_id: 2058
---
There is a Norwegian start-up called <a href="http://appear.in">AppearIn</a> which enables anyone to do online video conferencing with only a web browser. They utilize <a href="http://webrtc.org">WebRTC</a> to empower users to create rooms where they can hang out, do meetings, etc. We had the pleasure of having Ingrid form Appear In over for a visit and she suggested we'd contribute to their blog about use cases for appear in. That's how we became <a href="http://blog.appear.in/post/68370877054/use-case-7-for-appear-in-monitor-your-coffee-maker">use case #7</a> on their blog. Naturally this should've been an arduino powered thing with lot's of sophisticated technology, but we decided we'd just get something simple working first and then make it better later on.

<img src="https://media.tumblr.com/4599cbaf78271c30d5f01758671d9e1f/tumblr_inline_mwzef9lc9J1spb66d.jpg" align="center">

This is a really exciting platform as it empowers developers to have video/audio communication with little effort. Dealing with WebRTC, which is still a moving specification, can be quite challenging to implement properly. There's numerous corner cases and differences between implementations.

Update: Through the wonders of the internet, it turns out that we have brought the webcam thing full circle. A user on Hacker News <a href="https://news.ycombinator.com/item?id=6816125">posted a comment</a> which linked to a <a href="https://en.wikipedia.org/wiki/Trojan_Room_coffee_pot">wikiepedia article about the first webcam</a>. It turns out the use case for it was exactly the same as we had.