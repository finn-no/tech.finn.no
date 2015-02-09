---
layout: post
comments: true
date: 2015-02-10 12:00:00+0100
authors: 
title: "Finn.no wanted to split up their monolith, you wouldn't believe what happens next"
tags:
- Microservices
---

After working several years with (successfully) breaking up the legacy monolith, called iAD, Finn.no wanted to add structure to this process, and make sure that all 20 development teams work towards the same target. 
The target is a microservices architecture with small-ish autonomous services, owning their own data.

With that in mind we arranged our own two-day architecture summit for all Lead Developers, with a combination of external and internal presenters. 
The focus was on microservices, domain driven design, event driven architectures and modern programming techniques. 
We started off the first day with a long session.

Finn.no's
Chief Enterprise Architect [Sebastian Verheughe](https://www.linkedin.com/in/sebastian-verheughe) wanted to communicate his vision. 
This included topics such as strategic domain-driven-design, data-highways, and how to use event-driven architecture to further decouple our services.  

This theme continued, with the first external presenter. [Jan Ove Skogheim](https://www.linkedin.com/in/janovesk) from [Particular Software](http://particular.net/) talked about his experiences from Rikstoto, and how they had broken up a monolith into multiple small services. He also talked about the composite-UI pattern.

After lunch we continued with a presentation by [Karsten Mevassvik](http://no.linkedin.com/in/karstenmevassvik/en) from finn.no, of  the most important properties for a finn.no  microservice. These include:

- Is RESTful
- Owns its own data-store
- Team ownership
- Consumer Driven

The first day of the summit was concluded with a presentation of the CQRS architectural pattern. This presentation was held by [Idar Borlaug](https://www.linkedin.com/in/andreasberre) and [Andreas Berre](https://www.linkedin.com/pub/idar-borlaug) from WebStep.
They showed all the central concepts in CQRS, such as commands, event-stores, aggregates, sagas using very good examples to illustrate their concepts.

The second day of the summit started of with some hardcore programming. The frameworks of choice was node.js and RxJava. [Christopher Kolstad](https://www.linkedin.com/in/chriswk) started with an example of how a microservice could be implemented using Reactive Streams and RxJava. After that [Phillip Johnsen](https://www.linkedin.com/in/phillipjohnsen) presented a case using node.js in a real time context.
These two talks, gave a good introduction to new concepts that are not widely user in finn.no today.

During the second day several lightning talks were held, whenever there was a gap in the program. The talks, included topics as diverse as:

- Testing of MicroServices
- Unleash - Feature Toggles (https://github.com/finn-no/unleash)
- Event Driven Architecure in Finn.no
- Docker and Microservices.

Two more presentations should be mentioned. Firstly, one of the app-developers at Finn.no Haakon Eriksen http://no.linkedin.com/in/haakongjersvikeriksen/en, shared his opinions on what a perfect service looks like from a front-end developers viewpoint. The key point here was that the service developers should talk to their clients before and during development.

The last presentation was held by [Geir Pettersen](http://no.linkedin.com/in/geipette/en) where he talked about his approach to software architecture, "growing software" using examples from the development of the new airline-tickets search.
