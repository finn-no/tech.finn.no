---
authors: Tor Arne Kvaløy
comments: true
date: 2014-12-15 13:36:31+0100
layout: post
title: "React in the Real World"
tags:
- React
- JavaScript
- front-end
---

At [FINN oppdrag](http://www.finn.no/oppdrag/), a year ago we came to realize that our front-end code, written in JSP/jQuery, had become so large and complex that it affected our ability to produce features. We spent more time making sure the DOM manipulations were correct and did not break, than actually creating value for our users.

So we agreed that we needed a front-end framework to simplify our development and after looking into [Ember](http://emberjs.com/) and [Angular](https://angularjs.org/) we decided to go for [React](http://facebook.github.io/react/), recently open sourced by Facebook.

Our attraction to React was based on its promise of providing components with encapsulated state, fast automatic DOM manipulations, and the ability to render on the server.

##Migration
We started by porting some functionality that already existed, and our first discovery was that the lines of codes needed to make the exact same functionality was almost reduced by half. Secondly, the code became clearer and easier to understand. And thirdly, as a combination of the reduced lines of code, the cleaner code and no manual DOM manipulations, we got less bugs.

Since then all new features have been written with React, and when we are requested to make modifications to functionality written in the old code, we rewrite them to use React.

When migrating large pages to React we start from the inside, the deepest DOM nodes, and let it gradually grow till the whole page is reactified. This often happened through several iterations and deployments, as React and the old JSP/JQuery can quite peacefully coexist.

##Bundles
With React, HTML is represented as JavaScripts, so the JavaScript bundles will contain all the HTML, and hence grow as the pages grow. Instead of having a bundle for each page (very small), or having a bundle for the whole site (very big), we found a middle way where we have bundles based on roles, in our use cases contractor and owner. A possible improvement on this is loading JavaScript on demand with technologies like [webpack](http://webpack.github.io/).

##Flux
So if React is our first great discovery, the Flux pattern is very much in second place. [The Flux pattern](http://facebook.github.io/flux/docs/overview.html) decouples the logic from the views, in a different way than the traditional MVC-pattern. Logic and state are located in stores, and actions from the views to stores are asynchronous. When state in stores change, they will update React components that listen to changes, providing a single source of truth and unidirectional flow of data.

##Server-side rendering
Our next step, which is yet to be solved, is server-side rendering. Server-side rendering means having the server generate and return the whole HTML page to the browser, instead of having the browser render HTML after downloading the JavaScript. This is especially important for SEO (search engine optimization) and mobile users, as search engines rarely run JavaScript, and mobile users have low bandwidth hence will take longer time before the page shows. However, having the server return a fully rendered HTML to browser is in general good for perceived page load performance.

Server-side rendering requires running JavaScript on the server, and that is straightforward with Node. However it is not that straightforward in our Java/Tomcat/Spring backend stack. We envision three possible solutions for this problem:
1)	Running JavaScript on the JVM with the Nashorn JavaScript engine.
2)	Delegating execution of JavaScript to an external process running Node.
3)	Having Node run as smart-proxy in front of Java/Tomcat.

All these three solutions have their own benefits and challenges.

The first solution, running JavaScript in Java, has the benefits of close integration with the controller layer written in Java, and possibly making deployment easier, as the Java environment already has access to the JavaScript bundles. The downside of this approach seems to be performance, as the Nashorn JavaScript engine compiles and optimizes the JavaScript during the first runs, hence uses a long time to «warm up».  So unless we write a mechanism that warms up the code, the first pages will be very slow to load.

The second solution, which involves interacting with an external process running Node, has the benefits that JavaScript runs natively and very fast. The downsides are more complicated deployment process where the Node instance needs access to the JavaScript bundles, and the overhead of interacting with an external process, possible through HTTP or Thrift, can impact performance negatively.

The third solution, where Node is placed in front Tomcat, and works as a [smart-proxy](http://www.feedhenry.com/transforming-enterprise-node-js-feedhenry/), only enhancing certain pages, while simply forwarding the rest, has the benefits of executing JavaScript natively. While the downsides are more complicated deployment, and the possible complex process of executing certain JavaScript code found in HTML, and updating the HTML with the executed JavaScript, before it is served to the browser.

##Conclusion
So, apart from server-side rendering, which we are working on, our transition to React has been smooth and a great success. It has improved our codebase, made developers happy, and most importantly, improved our speed of delivering features, which in the end is the most important thing.


Tor Arne Kvaløy
Senior developer
