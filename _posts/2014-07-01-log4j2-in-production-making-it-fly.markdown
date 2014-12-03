---
authors:
- mick
comments: true
date: 2014-07-01 22:17:00+00:00
layout: post
slug: log4j2-in-production-making-it-fly
title: Log4j2 in production – making it fly
redirect_from: /log4j2-in-production-making-it-fly/
---

<br/>
Now that <a href="http://logging.apache.org/log4j/2.x/">log4j2</a> is the predominant logging framework in use at FINN.no why not share the good news with the world and try to provide a summary over the introduction of this exciting new technology into our platform.

<h3>let's just one logging framework</h3>
In beginning of September 2013 it became the responsibility of one of our engineering teams to introduce a "best practice for logging" for all of FINN.

The proposal put forth was that we can standardise on one backend logging framework while there would be no need to standardise on the abstraction layer directly used in our code.

The rationale to <strong>not standardising the logging abstractions</strong> was…

- nearly every codebase, through a tree of dependencies, already includes all the different logging abstraction libraries, so the hard work of configuring them against the chosen logging framework is still required,
- the different APIs to the different logging abstractions are not difficult for programmers to go between from one project to another.

While the rationale to <strong>standardising the logging framework</strong> was…  
- makes life easier for programmers with one documented "best practice" for all,  
- makes it possible through an in-house library to configure all abstraction layers, creating less configuration for programmers,
- makes life easier for operations knowing all jvms log the same way,  
- makes life easier for operations knowing all log files follow the same format.


<h3>Log4j2 wins HANDS down</h3>

Log4j2 was chosen as the logging framework given…
<ul><li>it provided all the features that logback was becoming popular for,  </li>
<li>between old log4j and logback it was the only framework that was written in java with modern concurrency (ie not hard synchronised methods/blocks),  </li>
<li>it provided a significant performance improvement (1000-10000 times faster)  </li>
<li>it consisted of a more active community (logback has been announced as the replacement for the old log4j, but log4j2 saw a new momentum in its apache community).</li></ul>

This proposal was checked with a vote by finn programmers
- 73% agreed, 27% were unsure, no-one disagreed.


<h3>when nightly compression jams</h3>
Earlier on in this process we hit a bug with the old log4j where nightly compression of already rotated logfiles were locking up all requests in any (or most) jvms for up to ten seconds. This fault came back to poor java concurrency code in the original log4j (which logback cloned). Exacerbated by us having scores of jvms for all our different microservices running on the same machines so that when nightly compression kicked in it did so all in parallel.  Possible fixes here were to  
a) stop compression of log files,  
b) make loggers async, or  
c) migrate over quickly to log4j2.

After some investigation, (c) was ruled out because there was no logstash plugin for log4j2 ready and moving forward without the json logfiles and the logstash & kibana integration was not an option. (a) was chosen as a temporary solution.


<h3>ready, steady, go…</h3>
Later on, when we started the work on upgrading all our services from thrift-0.6.1 up to thrift-0.9.1, we took the opportunity to kill two birds with the one stone. Log4j2 was out of beta, and we had ironed out the issues around the logstash plugin.

We'd be lying if we told you it was all completely pain free,
introducing Log4j2 came with some concerns and hurdles.

- Using a release candidate of log4j2 in production lead to some concerns. So far the only consequence was slow startup times (eg even small services paused for ~8 seconds during startup). This was due to log4j2 having to scan all classes for possible log4j plugins. This problem was fixed in 2.0-rc2. On the bright side – our use of the release candidate meant we spotted early and getting the upcoming initial release of log4j2 to support shaded jarfiles, of which we are heavily dependent on.
- Operation's had expressed concerns over nightly compression, raised from the earlier problem around nightly compression, that even if code no longer blocked while the compressing was happening in a background thread the amount of parallel compressions spawned would lead to IO contention which in turn leads to CPU contention. Because of this very real concern extensive tests have been executed, so far they've shown no measurable (under 1ms) impact exists upon services within the FINN platform. Furthermore this problem can be easily circumvented by adding the <a href="http://logging.apache.org/log4j/2.x/manual/appenders.html#TriggeringPolicies">SizeBasedTriggeringPolicy</a> to your appender, thereby enforcing a limit on how much parallel compression can happen at midnight.
- The new <a href="https://github.com/finn-no/log4j2-logstash-jsonevent-layout">logstash plugin</a> (which finn has actively contributed to on github) caused a few breakages to the format expected by our custom logstash parsers written by operations. Unfortunately this parser is based off the old log4j format, of which we are trying to escape. Breakages here were: log events on separate lines, avoiding commas are the end of lines between log events, thread context in wrong format, etc. These were tackled with pull requests on github and patch versions of our commons-service (the library used to pre-configure the correct dependency tree for log4j2 artifacts and properly plugging in all the different logging abstraction libraries).
- Increased memory from switching to sync loggers to async loggers impacted services with very small heap. The <a href="http://logging.apache.org/log4j/2.x/manual/async.html#AllAsync">async</a> logger used is based of the lmax-disruptor which pre-allocates its ringBuffer with its maximum capacity. By default this ringBuffer is configured to queue at maximum 256k log events. This can be adjusted with the "<a href="http://logging.apache.org/log4j/2.x/manual/async.html#MixedSync-Async">AsyncLoggerConfig.RingBufferSize</a>" system property.


<h3>simply beautiful</h3>
To wrap it up the hurdles have been there, but trivial and easy to deal with, while the benefits of introducing log4j2, and moving to async loggers, make it well worth it…
- The "best practice" for log4j2 included changing all loggers to be async, and this means that the performance of the FINN platform (which consists primarily of in-memory services) is no longer tied to and effected by how disks are performing (it was crazy that it was before).
- More and more applications are generating consistent logfiles according to our best practices.
- More and more applications are actually plugging in the various different logging abstractions used by all their various third-party dependencies.
- All the advantages people liked about logback.
- An easier approach to changing loglevels at runtime through jmx.
- Profiling applications in crisis easier for outsiders (one less low-level behavioural difference).
- Loggers are no longer a visible bottleneck in jvms under duress.
- And naturally <a href="http://www.javacodegeeks.com/2013/07/log4j-2-performance-close-to-insane.html">the performance increase</a>.

Because of the significant performance provided by the lmax-disruptor we also use the open-sourced <a href="https://github.com/finn-no/statsd-lmax-disruptor-client">statsd client</a> that takes advantage of it.

<a href="http://www.deviantart.com/art/Fire-and-Ice-129732715"><img src="http://wever.org/Fire_and_Ice_by_Canadian_fast_food.jpg"/></a>
