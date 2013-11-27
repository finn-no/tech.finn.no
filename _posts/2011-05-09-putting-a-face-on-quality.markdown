---
author: espen
comments: true
date: 2011-05-09 12:44:05+00:00
layout: post
slug: putting-a-face-on-quality
title: Putting a face on quality
wordpress_id: 529
categories:
- Behind the scenes
tags:
- feedback
- nodejs
- quality
- twitter
---

[caption id="attachment_771" align="aligncenter" width="600" caption="Photo by [dolarz](http://www.flickr.com/photos/dolarz)"][![http://www.flickr.com/photos/dolarz/2333292651/](http://tech.finn.no/wp-content/uploads/2011/05/flickrface-600x399.jpg)](http://tech.finn.no/wp-content/uploads/2011/05/flickrface.jpg)[/caption]

The topic of ensuring quality in the services we provide is a much debated topic in our industry [[1](http://gojko.net/2011/04/27/visualising-quality-initial-ideas)]. It tends to focus upon things such as testing and how to best automate it to try and reach a state of zero defects. These initiatives are great and we should all pursue them in our daily work. These debates tend to be very focused upon the technical side and how to prevent the intrusion of defects in our build cycle. A result of the technical focus is that you become abstracted from the real issues at hand and it becomes yet another ideological debate (or even worse yet another pissing contest between believers and non-believers).




At [FINN.no](http://www.finn.no) we try to always view things from an end-user perspective and technical quality is no different. So what does technical quality mean for our end users and do they really care?





## Making dreams come true


A service such as [FINN.no](http://www.finn.no) is much more than just a site where you view classified ads. It is a service where everyday people make some of their dreams come true. They buy the house they have been dreaming of in order to start a family. It is a place where you buy things to keep your kids safe and comfortable. So in order to answer the previous question, do they care about quality? Naturally. Down time on a Sunday at [FINN.no](http://www.finn.no) prevents people from buying the house, the car or the boat of their dreams.

You get the picture right? Quality of service means that we deliver on our promisse of being a marketplace you can rely on to make some of your dreams come true. Behind the discussions about unit-test coverage there is a family not getting their house, car or boat if you mess things up. When we debate whether to write tests up-front or do waterfall planning there is someone out there not getting their car sold. We get so caught up in technology some times that we actually think that it matters. In order to try and get more in touch with how our quality affects our users we realized we had to do something. When growing from a small company to a larger company you often end up lossing track of these things and we needed to correct this.


## Continuous improvements a.k.a. Lean


At [FINN.no](http://www.finn.no) we have some people work solely with teaching us [continuous improvement](http://en.wikipedia.org/wiki/Continuous_improvement_process). They try to help every team or department to learn the [Lean](http://en.wikipedia.org/wiki/Lean_software_development) way of working and resolving issues. By spreading their knowledge across the company they start a lot of cross-team-department-initiatives which would not happen without them.

Bulding quality into your product is one of the essential things towards creating an amazing user experience, but the problem is how do you do this on a regular basis? There are numerous tools available for all kinds of testing and quality analysis of code and things like that. These tools are all good and you should use them. However, they all fail to bring you the one insight which is the most valueable yo your business: what does your users actually think about what you do.

Our customer service and architectural teams have both been working to try and establish a way of working which ensure continuous improvement in what we do. This has resulted in an initiative to try and visualize how our level of quality effects our users and customers.


## FINNback or Tweet Board


One easy way of putting a face on quality was to utilize the [Twitter Streaming API](http://dev.twitter.com/pages/streaming_api) which enables us to see what people think about our service. Thanks to the [brilliant engineers at Twitter](http://dev.twitter.com/) this was easily accomplished in just a few hours and we had a [node.js](http://nodejs.org) application which displayed tweets about us on monitors/TVs in our cantina and in our reception. Thanks to the awesomeness of the [Twitter Streaming API](http://dev.twitter.com/pages/streaming_api) we are able to pull both the profile picture, the profile background image and the number of followers into our application in order to give a more human feel to the tweets coming in. This was critical as it makes the tweets real as they come from real live people who we effect with our work. In the sample screenshot of the _FINNback_ application  we get a confirmation of something we are painfully aware of, [we should have an app](http://twitter.com/#!/AnetteBastnes/status/65843803675820032).
[![](http://tech.finn.no/wp-content/uploads/2011/05/FINNbækk-brettet-600x333.jpg)](http://tech.finn.no/wp-content/uploads/2011/05/FINNbækk-brettet.jpg)

Doing this Twitter feed visualization is just a first step and we are working hard towards creating more real-time feedback visualization in order to make sure every one of our employees knows exactly how our users feel about what we do. We are confident that this is a great way of getting focus on technical quality and to make sure we work even harder to provide a service which makes our users dreams come true.
