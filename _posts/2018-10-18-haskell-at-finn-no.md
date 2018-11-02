---
layout: post
comments: true
date: 2018-10-18 11:00:00+0200
authors: Sjur Millidahl
title: "Haskell at FINN.no"
tags:
- Haskell
- functional
---

### Favorites
FINN has this feature where users can mark classified ads as a favorite, making it easy to come back to certain ads later. The feature has been a round quite a while, and the systems involved, both frontend and backend were becoming quite hard to maintain. So the AdView-team set out to redesign the whole stack. This blog post will focus on the backend API which is written in Haskell.

### Haskell
Haskell is a purely functional programming language, with a powerful type system. The ability to express intent using types brings correctness, and the composition of a large program as small, independent building blocks makes it easy to reason about the code.

A large ecosystem of production grade libraries are available from [Hackage](https://hackage.haskell.org/). We make use of Servant (API), Aeson (JSON), Hasql (postgres) and many more. Servant is a type-level API, meaning the "sum" of all our endpoints become a distinct type. This, in turn, means that
refactoring our API gives us heavy compiler assistance. More than once during development, we had the need to do major changes in the endpoints (verbs, paths, request body, responses) and found that once every compilation error was resolved, the new API was working correctly.

To manage external packages, build and test code, we use [Stack](https://haskellstack.org), because of its familiarity to maven, gradle and sbt-users. `stack build` will compile the project, `stack test` run all the tests. Stack pulls down [Glasgow Haskell Compiler ("GHC")](https://www.haskell.org/ghc/) along with required libraries. It also supports building and running of the final program in Docker.

Now Docker is important, because of the cloud infrastructure in FINN; any technology running in Docker can be used as a new Micro Service. 

### Experiences
So what are the downsides to using Haskell? Well - there is really only one. We have to have a certain amount of developers, at least in our team, know Haskell. We meet this challenge in three ways. Firstly by doing a Haskell course internally at FINN. 21 of our developers have signed up for an [Introductory Haskell Course from the University of Glasgow](https://www.futurelearn.com/courses/functional-programming-haskell/), secondly we cheated a little by simply recruiting another Haskell developer. And lastly, we will put some effort into the [Oslo Haskell Meetup group](https://www.meetup.com/Oslo-Haskell/), hopefully spawning even more Haskellers.

Really? No more downsides? Well, it should be mentioned that our build-times on Travis CI are not super-awesome-great. We are currently looking at 8-9 minutes for a complete build (including integration tests). But we can live with this.

As for performance, the [Warp web server](https://hackage.haskell.org/package/warp) is doing an excellent job of spawning lightweight threads and keeping CPU and memory usage low. An indication of memory and CPU usage is given below. Note that the old API still have way more traffic, so a direct comparison is very unfair!

<figure>
    <img class="center-block" src="/images/2018-10-18-haskell-at-finn-no/performance.png" alt="haskell performance as seen by kubernetes" title="haskell performance as seen by kubernetes" />
    <figcaption style="text-align:right; font-style:italic;"><strong>favorite-api</strong> is the new API (Haskell).<br /><strong>classified-favorite-management-server</strong> is the old API (Java, hence the long name).<br />You can barely see the <strong>32 MB</strong> memory footprint of the new API in the graphs!</figcaption>
</figure>

### Check it out
We are really looking forward to putting more load on our new API, and doing more Haskell in the future.
You can check out the redesign of [favorites](https://www.finn.no/favoritter) for yourself.
And if you would like to learn some Haskell, a great starting point is [Learn You A Haskell For Great Good](http://learnyouahaskell.com/)
