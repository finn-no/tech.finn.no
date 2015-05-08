---
layout: post
comments: true
date: 2015-03-10 15:37:49+0100
authors: Nicolai Høge
title: "Eric Evans in the house"
tags:
- architecture
---

In our continuing quest to break up our old monolith and create a more flexible and scalable architecture, we have realized that we need to some new tools and methods. One of the more promising approaches was [Domain Driven Design](http://domainlanguage.com/ddd/), a term coined by [Eric Evans](https://www.linkedin.com/in/ericevansddd) in his [Big Blue Book](http://www.amazon.com/exec/obidos/ASIN/0321125215/domainlanguag-20)


Quite a few people in FINN has read (at least parts of) the Domain-Driven Design book, and we did some preliminary efforts to map our domains and look at our bounded contexts. We quickly realized that this was a very complex exercise, even with highly motivated tech and product people present. We figured we needed some help to get us on the right track. If you need help and information about DDD, why not get it straight from the horse's mouth? So we got Eric Evans to visit us for a week at the end of February.

We needed to make the most of our time with Eric, so we set up a rather extensive agenda:

Day | Subject | Attending
----|---------|----------
Monday | Strategic Domain Driven Design Course | FINNs CTO, our functional product directors, the members of our enterprise architecture group including our Chief Enterprise Architect, several key developers and a couple of our international friends from Schibsted Classified Media
Tuesday | Strategic Domain Driven Design Course cont'd | Same as Monday
Wednesday | Workshop: Ad concept and our miscellaneous vertical | Chief Enterprise architect, EA group, Functional Product Director, Product Owner, Lead Developer
Thursday | Workshop: Job vertical and company profile | Chief Enterprise architect, EA group, Functional Product Director, Product Owner, Lead Developers
Friday | Presentation for everyone in FINN.no, plus workshop: Communications | Chief Enterprise architect, EA group, Functional Product Director, Product Owner, Lead Developers

The course on Monday and Tuesday was extremely helpful in getting everyone up to speed on the terms and method of strategic domain driven design.
![Eric Evans on the flipover](/images/2015-03-10-eric-evans-in-the-house/eric_flipover.jpg "Eric Evans on the flipover")

The workshops the rest of the week focused on understanding the challenges involved in each area, and trying to map the bounded contexts of the existing systems.

Some lessons we learnt during the week.

* When you are trying to create a context map, start with a very concrete example of how the system works. Which components interacts with each other, what data is passed between them, exactly what does the input and output look like
* Use a *huge* whiteboard
* Plan to use at least a few hours on every session. Doing a context map for even a part of a system may take a lot longer than you think.

Then you can manage to turn something like this:

![ad management example](/images/2015-03-10-eric-evans-in-the-house/ad_management.jpg "Ad management example")

Into a nice map like this:

![ad context map](/images/2015-03-10-eric-evans-in-the-house/ad_management_context_map.jpg "Ad context map")

Our key findings on bounded contexts:

* Bounded contexts represents a higher order logical representation of a solution
* They may span several of FINNs current "microservices"
* They are useful for understanding the high level architecture, and how teams and solutions depend on each other

> The context map is a tool for discussions, and the boundaries and relationships are subjective.

Sample map focused on small subset of our system.
![bounded contexts microservices](/images/2015-03-10-eric-evans-in-the-house/bounded_context_microservice.png "Bounded contexts and microservices")

Example trying to map a larger part of our system.
![bounded contexts microservices](/images/2015-03-10-eric-evans-in-the-house/finn_context_map.png "Bounded contexts and microservices")


Another thing we focused on was the concept of distilling the core. Figuring out which parts of your system that gives you a competitive advantage, and deserves extra attention. As part of this process it is also important to identify your supporting and generic domains. The tricky part is that it is really your business strategy that dictates what your core domain is.

So to sum things up: Having Eric Evans on-site for such a long time gave us invaluable insight into DDD, and how to apply it in our own business. Together the bounded context map, and our identified domains can help us drive development in the right direction. They are tools for discussion, not a framework that removes the need for highly competent developers.

