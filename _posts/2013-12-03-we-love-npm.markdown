---
authors:
- espen
comments: true
date: 2013-12-03 10:38:00+00:00
layout: post
slug: we-love-npm
title: We love npm
---
<img src="https://scalenpm.org/img/npm.png" alt="npm Logo" />
What? Why is FINN.no donating to scale NPM? I thought you guys were a pure Java shop? It is true, we used to be a pure Java-shop. However over the past three years we have adopted new technologies to solve specific problems. We have used <a href="https://www.ruby-lang.org/en/">Ruby</a> and <a href="http://cukes.info/">Cucumber</a> for some time to make a platform for continuos delivery and it has worked out beautifully! Our front-end developers have been forced to deal with out dated and not suitable tools for doing their job. This is largely due to the fact that all innovation when it comes to front-end development does not happen in the Java community. Most of the exciting tools are written in Node and this has become a frustration and a challenge for us.

In the past year FINN have been gradually making a transition away from using only Java-based tools for front-end development and towards a NodeJS powered tool set. We are now at a point were we are on the brink of rolling this out for our projects. Having worked with Node for a while we have learned to appreciate the Node ecosystem which is NPM. Being part of such a vibrant ecosystem of modules makes the transition easier and it also inspires us to become better at giving back. Therefore we are trying to give back to NPM, when we can.

When the <a href="http://scalenpm.org">scale NPM</a> campaign was launched it was obvious that this was something we wanted to be apart of. It is an investment in our own happiness in a sense, as NPM is becoming a very important part for our technology portfolio.
<h2>Nodeify all the things</h2>
So were is it that we use Node in our technology stack today? Earlier this year we moved away from <a href="https://code.google.com/p/js-test-driver/wiki/">JsTestDriver</a> in favor of <a href="http://karma-runner.github.io">Karma-runner</a>. This meant that we needed to create a trojan horse containing the goodness of Node/NPM into existing Java projects without causing too many problems for developers with no knowledge of Node. A part of this scheme was the <a href="https://github.com/eirslett/frontend-maven-plugin">frontend-maven-plugin</a>, which enables us to have control of which Node projects use and allows developers without Node previously installed to build projects and run tests without having to learn anything about Node.

Currently we are working towards removing the need for using Maven to build and deploy pure JavaScript projects using Node. The end result is of course to have more web applications built using Node. Today we have just two which are running in a production environment.
