---
layout: post
comments: true
date: 2015-02-16 08:50:16+0100
authors: Sjur Millidahl
title: "Java 8 workshop at Finn.no"
thumbnail: "http://tech.finn.no/images/2015-02-06-java-8-workshopp-at-finnno/DSC_0091.JPG"
tags:
- java
- java8
- functional
- lambda
- workshop
---

<figure>
  <img src="/images/2015-02-06-java-8-workshopp-at-finnno/DSC_0091.JPG" alt="Pondering on the right lambda for the job" />
  <figcaption>Pondering on the right lambda for the job</figcaption>
</figure>

With Java 8 comes a whole new set of language features. It even challenges the imperative coding-style of the Java programmer.

Java is a core language in Finn.no. More and more of our java-modules are being built with Java 8, adopting new features of the language.
A workshop was warranted, with the goal of bringing every developer up to speed on the functional paradigm of Java 8.

In Finn.no, we want programmers to do stuff like this

{% highlight java %}
db.fetchLastDayAds()
  .stream()
  .filter(ad -> "bap-webstore".equals(ad.getAdType()))
  .flatMap(ad -> ad.getContacts().stream())
  .distinct()
  .flatMap(per ->
    Optional.ofNullable(contact.getEmail()).map(Stream::of)
    .orElseGet(Stream::empty))
  .peek(contact -> LOG.trace("Sending notification to "+per))
  .forEach(this::sendNotification);
{% endhighlight %}

While avoiding stuff like this


{% highlight java %}
IntStream.iterate(0, i -> (i + 1) % 2)
         .parallel()
         .distinct()
         .limit(10)
         .forEach(System.out::println);
{% endhighlight %}
(Locking up all cores on a CPU is bad, and should only be done when the machine is right about to become [self-aware](http://xkcd.org/1046/) and turn against you.)

<figure>
  <img src="/images/2015-02-06-java-8-workshopp-at-finnno/DSC_0092.JPG" alt="Several lambda-arrows pointing in the right direction here"/>
  <figcaption>Several lambda-arrows pointing in the right direction here</figcaption>
</figure>

We split the workshop into two half days. The first day was dedicated to streams and lambdas. Everyone seemed keen on getting those tests green (a rhyme!).

Day 2 we raised the bar with Optional<T> and our in-house version of Either<L,R>. With these structures, much more code can be written functionally in a world where values might not exist (be null), and things may go wrong (throw exceptions).

<figure>
  <img src="/images/2015-02-06-java-8-workshopp-at-finnno/DSC_0095.JPG" alt ="We need power.. lots of power!" />
  <figcaption>We need power.. lots of power!</figcaption>
</figure>

Both the word "monad" and the phrase "monadic domain" was uttered several times, but we still saw very few making the swoooosh-sound while flying a hand over their head (the internationally recognized sign of communicating that a topic is beyond mental capacity).

This might mean that the timing was good, and developers are interested in the new features of Java 8.

You may [checkout the project and do the tasks yourself](https://github.com/mariatsji/java8-workshop.git), by making the failing tests green.
