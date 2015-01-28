---
layout: post
comments: true
date: 2015-01-28 14:40:36+0100
authors: Andersos
title: "From Wordpress to Jekyll"
tags:
- Wordpress
- Jekyll
---


[![Jekyll](/images/2015-01-28-from-wordpress-to-jekyll/jekyll.png)](http://jekyllrb.com/)

Last year we decided to move our developer blog from [Wordpress](https://wordpress.org/) to [Jekyll](http://jekyllrb.com/). One of the main reasons were the constant security vulnerabilities of Wordpress. We were hosting Wordpress on our own hardware. We pleased our operations team when they could shut down the service. [Github](https://pages.github.com/) provides hosting for Jekyll projects and that was one of many reasons we landed on that solution.

Other reasons why Jekyll is a good fit:
- Static files (blazing fast)
- Version controlled files ([git](http://git-scm.com/)) and the source is available [here](https://github.com/finn-no/tech.finn.no)
- [Github](https://github.com/orgs/finn-no/people) handles username and passwords
- Easy to run [locally](https://github.com/finn-no/tech.finn.no/blob/gh-pages/README.md)
- You can use your favourite editor to write in Markdown or just plain HTML

Github restricts the number of plugins you can use down to this [list](https://pages.github.com/versions/). We really just wanted a simple blog platform so that was no deal breaker. We even get both @Andersos ([@mentions](https://github.com/blog/821)) and :+1: (Emoji).

We [landed on](https://github.com/finn-no/tech.finn.no/issues/1) using [Disqus](https://disqus.com/) for comments which is loaded on the [client side](https://github.com/finn-no/tech.finn.no/blob/gh-pages/_layouts/post.html#L41). We made a [design template post](http://tech.finn.no/2010/01/01/design-template/). We use that post to check out the design of different elements. That post is also a good starting point for people new to markdown. We are using [Html-proofer](https://github.com/gjtorikian/html-proofer) to test the HTML of the site. Html-proofer checks that all the urls and images on the site are working. Another tip for improving blogposts is to use a tool like [Hemingwayapp](http://www.hemingwayapp.com/).

This developer blog is a place where we at Finn.no can share what we are working on and our interests. If you have suggestions or ideas for what we should write about please leave a comment under this post.

List of 10 most visited posts:
- [The Ultimate view tiles 3](http://tech.finn.no/2012/07/25/the-ultimate-view-tiles-3/)
- [XSS protection whos responsibility](http://tech.finn.no/2011/04/08/xss-protection-whos-responsibility/)
- [Dependency injection with constructors](http://tech.finn.no/2011/05/12/dependency-injection-with-constructors/)
- [Conquering the kingdom of Java with a Node.js trojan horse](http://tech.finn.no/2013/06/06/conquering-the-kingdom-of-java-with-a-nodejs-trojan-horse/)
- [When do you know how much testing is enough](http://tech.finn.no/2012/05/21/when-do-you-know-how-much-testing-is-enough/)
- [Given the git](http://tech.finn.no/2013/03/20/given-the-git/)
- [Log4j2 in production making it fly](http://tech.finn.no/2014/07/01/log4j2-in-production-making-it-fly/)
- [I wish I knew my consumers Maven reverse dependency](http://tech.finn.no/2013/01/31/i-wish-i-knew-my-consumers-maven-reverse-dependency/)
- [Email providers in Norway](http://tech.finn.no/2014/11/21/email-providers-in-norway/)
- [Dark launching and feature toggles](http://tech.finn.no/2013/06/20/dark-launching-and-feature-toggles/)
