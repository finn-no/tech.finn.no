---
layout: post
comments: true

date: 2017-10-17 08:56:49+0200
authors:
- Arild Nilsen
- Sjur Millidahl
title: "Akka workshop at Finn.no"
tags:
- akka
- jvm
- workshop
- actor model
- java
- kotlin
---

<figure>
   <img class="center-block" src="/images/2017-10-17-akka-workshop-finn-no/DSC_0083.JPG" alt="alt" title="Akka workshoppers hard at work!" />
   <figcaption style="text-align:center; font-style:italic;"> Akka workshoppers hard at work!</figcaption>
</figure>


In FINN.no we have experience in running distributed systems, and we often rely on asynchronous communication between our applications. [Apache Kafka](http://tech.finn.no/2015/09/22/the-process-of-using-kafka/) is the work-horse on the house, and it's doing an excellent job for us.


But this doesn't mean that we shouldn't learn new things - or look at other solutions.


Akka is an implementation of [the Actor Model](https://en.wikipedia.org/wiki/Actor_model) - a concept of concurrent processing first published in 1973. The Actor Model is all about two ideas: Actors and Messages.


<figure>
   <img class="center-block" src="/images/2017-10-17-akka-workshop-finn-no/actor-model.png" alt="alt" title="Actors sending messages" />
   <figcaption style="text-align:center; font-style:italic;"> Actors sending messages</figcaption>
</figure>


A number of Actors communicate to each other using messages. An Actor will process its received messages sequentially, and send messages to other actors asynchronously. It can also keep state, perform side-effects and supervise other actors. This small hand-full of operations makes the Actor Model easy to reason about and within one Actor we need not worry about the complexity of concurrency - one message is processed after another.


The power of the Actor Model comes from combining (often specialized) Actors in a supervised hierarchy. Concurrency and scalability comes naturally as the sending of messages between them are asynchronous. Reliability and uptime comes from a let-it-crash philosophy and supervision done through special Supervisor-Actors.


<figure>
   <img class="center-block" src="/images/2017-10-17-akka-workshop-finn-no/DSC_0085.png" alt="alt" title="Arild explaining the async monad!" />
   <figcaption style="text-align:center; font-style:italic;"> Arild wrapping heads around the async monad!</figcaption>
</figure>


The workshop was a hands-on with a few slides between the exercises. You can [do the tasks yourself by cloning](https://github.com/mariatsji/akka-workshop) and checking out the master-branch (for java) or the kotlin-branch.


[Slides](https://mariatsji.github.io/akka-workshop/)
[Resources](https://akka.io) 

