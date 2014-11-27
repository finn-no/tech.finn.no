---
authors:
- mck
comments: true
date: 2014-11-25 13:00:00+00:00
layout: post
slug: apachecon-budapest
title: ApacheCon Budapest 2014
redirect_from: /apachecon-budapest/
tags:
 - apache
 - conference
 - cassandra
 - hadoop
 - docker
 - mesos
 - diversity
---

Last week was Europe's <a href="http://drbacchus.com/apachecon-budapest-2014/">ApacheCon</a>, held in Budapest.

A refreshingly "<a href="http://iamfortress.wordpress.com/2014/11/23/a-newcomers-perspective-on-apachecon-europe-2014/" title="Shawn McKinney">laid-back and no-nonsense</a>" conference free from the douchebaggery that comes from big sponsors and marketing. This year it was held in the awesome 5-star Corinthia Hotel, and consisted of a welcoming and cheerful vibe, a place for apache members, committers, and all, to get together and better know each other. Apache continues to grow as the foundation for strong communities building trusted open sourced solutions, for running half the internet, and providing a substantial amount of code to your java stack.

Some hot topics were Docker, Mesos, Spark, Cassandra, CouchDB, Hadoop, Solr, OpenOffice, and the need for greater diversity in our communities.

Spark is all the rage because of its brevity and simplicity, but isn't really a complete solution yet simply because it doesn't scale in many situations.

Docker is awesome, and looks to take over the testing domain, but it's remains limited and out of production as long as it has no network stack implementation. There was a very cool demonstration of Mesos and Aurora increasing production utilisation, even allowing in quiet periods for development/testing servers to come in.

The next release of YARN (2.6) will see support for long-lived services, whereby we can transform many of our hadoop jobs with very little effort into true streaming solutions. YARN will also be able to <a href="http://blog.sequenceiq.com/blog/2014/11/20/yarn-containers-and-docker">deploy docker containers</a> through its clusters, this could be a very nice solution for our batch jobs. Putting these together and you see that the technical separation between streaming and aggregating solutions fades away and it really just boils down to what data structures you solve each use case with. Talking with a hadoop committer it came to light that FINN could be running the fastest hadoop cluster that they know of, given HDFS runs on SSDs and is dedicated just for hadoop internals.

Of no surprise Solr dealt a lot with scaling and <a href="http://events.linuxfoundation.org/sites/events/files/slides/HighPerformanceSolr.pdf">performance</a>, while Cassandra presented use-cases from across the industry. It felt that big tech companies are migrating their databases over to Cassandra not just for availability but often simply for the sake of performance. "<a href="http://www.slideshare.net/planetcassandra/cassandra-summit-2014-deploying-cassandra-for-call-of-duty">Call of Duty</a>" was rewritten from mysql to cassandra, resulting in the 95th percentile for requests going down to under 500μs. You don't need complicated cache strategies when your backend is this fast!

Other Cassandra talks included Eric Evans presenting <a hef="http:/opennms.github.com/newts">Newts</a>, an lazy-aggregating time-series solution built with graphing and lucene search inbuilt. And <a href="http://events.linuxfoundation.org/sites/events/files/slides/Intro-To-Usergrid%20-%20ApacheCon%20EU%202014.pdf">Apache UserGrid</a>, a fascinating project built, it is your LAMP stack, a BaaS framework, for the mobile-first world.

Patrick McFadin held a tutorial putting together kafka, cassandra, and spark, to create a very <a href="https://github.com/killrweather/killrweather">elegant scaling streaming solution</a>. Patrick also discussed the performance gain of putting spark RDD data in cassandra rather than using files on disk. Chatting with both Eric Evans (he who coined "NoSQL") and Patrick highlighted that the throughput per node of our Cassandra cluster is leading in the industry, it's just to add more servers and reap the benefits.

And yours humbly presented to a packed room our <a href="https://prezi.com/xgjrvkxhxkg8/apachecon-cassandra-and-hadoop-finnno/">Cassandra and Hadoop</a> use-cases. Making mention to the need for better gender balance within Apache, Schibsted's role in online classified markets across Europe, the need to look beyond current trends in our industry when designing systems, and having a healthy preference towards AP systems in the enterprise.

Absolutely a conference I'd recommend to infrastructure, operations, and back-enders serious about their technologies.


<p class="centerify">Oh… and Happy <a href="https://blogs.apache.org/foundation/entry/the_apache_software_foundation_celebrates2">15th birthday</a> Apache!<br/><img src="http://farm8.staticflickr.com/7533/15816582111_4b19b886c5_c.jpg"/></p>

More photos from the conference <a href="http://events.linuxfoundation.org/events/apachecon-europe">here</a>



