---
author: espen
comments: true
date: 2012-06-23 18:32:28+00:00
layout: post
slug: going-all-out-with-the-strap-on-project
title: Going all out with the Strap-on Project
wordpress_id: 1353
categories:
- Interface development
tags:
- CSS
- frontend
- html
- oocss
---

In January we started on an ambitious project called "the Strap-on project". The infantile name aside this project is about rewriting our entire front-end tier using [Object Oriented CSS (OOCSS)](http://oocss.org/), written by [Nicole Sulivan](http://www.stubbornella.org/), as the secret sauce to achieving the desired results.





## Why OOCSS?


![](http://www.sonaa.co.uk/assets/images/articles/oocss-lrg.jpg)


At FINN we have teams grouped by business units and all of them have developers who contribute with code on our site. This is provided us with a head ache with CSS, because there are no clear idioms for how to best write CSS. In our case this had cause teams to create their own "islands" of CSS by using # trails, train wreck selector classes and !important-wars. Teams had duplicate CSS for the same layout and we had a hard time keeping the user experience consistent across different parts of our service.




We had way too much CSS code in total and we sent loads of CSS on each page request with just a small percentage actually being used. This made page load slow and rendering slow. Page rendering speed is equal to money, so we had to change something.




## OOCSS - making CSS coding a thing of the past




OOCSS is a framework on which to build your own. It provides with a base set of modules and concepts which is essentials to building stuff in HTML with CSS. Grids, modules, lines, etc. These base modules provides an abstraction on top of CSS which makes authoring of CSS a thing of the past. In order to layout basic pages all we do is to use the building blocks and put together what ever you want. Basic boring stuff such as clearing and the box-model is no longer anything you need to worry about, it's taken care of. This enables developers to focus upon fulfilling business requirements instead of battling CSS differences in browser time and time again. 





## Why not LESS, Compass or stuff like that?




The CSS language abstractions that exist out there are all very cool projects and make a whole lot of sense to use for a lot of projects. However, in our case choosing one of these tools right now would not provide us with some of the benefits we are looking for.





At FINN we have a problem that we are duplicating CSS across our development teams. This makes creating a consistent user experience and making global changes harder than it should be. Using an abstraction would help us hide some these problems, but would not solve the underlying issue which is that we create too much code. OOCSS and its rigorous set of rules helps reduce the amount of code drastically and provides rigid rules which prevents duplication across teams. What we are looking for are:






  * Improve rendering speed


  * Improve development speed


  * Easier to provide a consistent user experience





## A touch of Bootstrap too




The engineers over at Twitter has created an amazing framework, [Bootstrap](http://twitter.github.com/bootstrap/), which is hugely popular all over the world. We have indeed paid close attention to how they have done and we are very much inspired by their work. However, going all out and just adopting Bootstrap was not an option. We feel it is too bloated and it does not provide the speed and performance benefits OOCSS gives. 
Having said that, a lot of our setup with forms and form elements is influenced by how Bootstrap does things.





## Half way, how does it look?




In short,iIt looks pretty darn impressive! We have removed more than twenty CSS files and reduced the amount of CSS code lines with more than thirty thousand (this does say quite a bit of the mess we where in, I know). We have migrated the motorized vehicles, jobs, real estate sections and the front page.










Before
After







Number of CSS files


130


38





Lines of CSS code


32 789


2 927





Lines of section specific CSS


727


89

  



These are pretty impressive results and very much in line with what Nicole is talking about in her presentations about OOCSS and performance. 




## is it all singing, all dancing?




Of course not! The responsive bit is something we are looking at reworking. Team Oppdrag has created one way of solving this and Team Reise another. For the Strap-on project we will probably end up with something in between. There are some things you need to take into consideration when choosing an approach:
How much control do you have over the markup being written? Or to put it in another way, how many people are working with the same code? The more people, the harder it is to apply very strict rules as people will be "tourists" in the code base and might have a hard time getting it. 




Another big challenge is to provide enough support and training to help everyone utilize the OOCSS framework so we can continue to reap benefits from what we have done, but that is probably some other blog post
