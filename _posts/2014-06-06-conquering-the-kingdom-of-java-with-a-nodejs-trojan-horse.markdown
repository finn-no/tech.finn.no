---
authors: espen
comments: true
date: 2013-06-06 07:36:00+00:00
layout: post
title: Conquering the kingdom of Java with a NodeJS trojan horse
redirect_from: /conquering-the-kingdom-of-java-with-a-nodejs-trojan-horse/
---
<img src="http://upload.wikimedia.org/wikipedia/commons/6/6e/Trojan_Horse_by_A_Yakovlev_1911.jpg" alt="trojan horse">
Most of FINN.no is built with Java and we've been a Java shop for 6-7 years. With the professionalization of front-end development we saw that the tools available in the Java Web Development ecosystem was preventing us from delivering rapidly and with high quality.
The trend was that the tools we used weren't keeping up with the pace set by people creating tools in <a href="http://nodejs.org/">Node</a>'s <acronym title="Node Package Management">NPM</acronym> module ecosystem. When working on pet projects we could use these tools, but in our daily job we were working with second rate tools. This was a frustration for many of us, so we set out to work out how we could utilize Node without having to rewrite all our applications.

<h2>In the belly of the beast that is Maven</h2>
We use <a href="http://maven.apache.org/">Maven</a> to build and test all our applications. This includes continuous deployment and quality analysis. Our reporting tools requires projects to be built with Maven in order to work. To introduce Node-based tools for front-end tasks, we had to be able to run them from Maven.

The easiest way to get started is just to use the Maven exec plugin. It allows you to run commands like on the command line. This approach worked, but there was one catch: it relied upon <a href="http://nodejs.org/">Node</a> + <a href="https://www.npmjs.org/">NPM</a> to be installed on every developer machine as well as in our <acronym title="Continuous Integration">CI</acronym> environment. Forcing 100+ developers to install Node and keep up with versions was something which wasn't really acceptable for the rest of the organization. Enter The Maven Front-end Plugin

<h2>Behold! The Frontend Maven plugin</h2>
<a href="https://github.com/eirslett">Erik Sletteberg</a>, who started out working as a summer intern and is now working at FINN.no full time, helped solve the problem of Node having to be installed. He created the <a href="https://github.com/eirslett/frontend-maven-plugin">Frontend Maven Plugin</a> which install Node+NPM in the project folder. The plugin also has tasks for running NPM, <a href="http://gruntjs.com/">Grunt</a>, <a href="http://gulpjs.com/">Gulp</a> and similar.
Because of the tool we were able to use Node within a Maven web application module, which was awesome! It meant we could use build-, test and code analysis tools developed in Node and have them report into our existing tool chain.

<h2>Independence with Standalone Node modules</h2>
In the Java stack there is no real support for distributing front end dependencies for things like JavaScript, CSS, etc. There are plugins for things like Require-js for JavaScript, but they were all suffering from the same problem: the plugins couldn't keep up with the pace set by the Node ecosystem when it came to iterating on these kinds of tools.
Our setup at the time was to split out any front-end resource which needed to be distributed into Maven Web Application modules and include them like any other Maven dependency in the POM. The release process for Maven artifacts is tedious. When importing dependencies into an existing Maven module you had to perform all kinds of URL rewriting to make paths correct. In short, this was not a very smooth way of sharing client side resources.

Having Node and NPM running inside Maven, it was only natural for us to look at how we could create <a href="http://en.wikipedia.org/wiki/CommonJS">Common JS</a> modules of our existing JS modules which were now in Maven. The short term goal was to get rid of Maven while developing these modules. The long term goal is to have client side dependencies declared as regular Node dependencies in projects.

<h2>Maven-deploy</h2>
In order to make the transition as smooth as possible we needed to have Node modules which we were able to deploy to Maven repositories in addition to our private NPM registry. <a href="https://github.com/gregersrygg">Gregers G. Rygg</a> from the Front-end Core Team set out to create the <a href="https://github.com/finn-no/maven-deploy">maven-deploy</a> module which solves this (if you use Gulp, you can use the <a href="https://www.npmjs.org/package/gulp-maven-deploy">gulp-maven-deploy</a> plugin). It can either install the artifact locally under M2_HOME or deploy it to a remote repositories. This way we could have Node modules behave as if they were Maven modules, which in turn meant that existing Java Web Application didn't have to be changed in order for us to use Node.

<h2>In closing</h2>
This was by no means the fastest way to introduce Node into an existing architecture. If you're a small shop with few people, you should adopt a more aggressive approach. Our approach however works beautifully for those of you working in larger organizations were you need to do things in a more subtle way. Taking small steps and always making sure everyone can perform their job as normal was one of our keys to succeeding. If we'd gone all out and created additional work for many developers, our Node adventure would've never happened.

<a href="//github.com/leftieFriele">Espen Dalløkken</a> from the Front-end Core Team <a href="http://2014.boosterconf.no/talks/232">did a short lightening talk</a> about this very subject at <a href="http://www.boosterconf.no/">Booster Conference</a> 2014 in Bergen:
<p><a href="http://vimeo.com/89938193">Conquering the wicked kingdom of Java with a NodeJS Trojan horse - Espen Dalløkken</a> from <a href="http://vimeo.com/boosterconf">Booster conference</a> on <a href="https://vimeo.com">Vimeo</a>.</p>