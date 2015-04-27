---
authors: mick
comments: true
date: 2015-04-27 09:00:00+00:00
layout: post
title: Finding Gold in Big Data
---
In the <a href="/2012/08/06/foraging-in-the-landscape-of-big-data/">previous article</a> we introduced our own introduction into the world of Big Data, and explored what it meant for FINN. Here we'll go into the technical depth about the implementation of our Big Data needs.

## rehashing the previous article
FINN is a busy site, the busiest in Norway, and we display over 80 million ad pages each day. Back when we it was around 50 million views per day, the old system responsible for collecting statistics was performing up to a thousand database writes per second in peak traffic. Like a lot of web applications we had a modern scalable presentation and logic tier based upon ten tomcat servers but just one not-so-scalable monster database sitting in the data tier. The procedure responsible for writing to the statistics table in the relational database was our biggest thorn.

At the time we were in the process of modularising the FINN web application. The time was right to turn our statistics system into something modern and modular. We wanted an asynchronous, fault-tolerance, linearly scaling, and durable solution. 

## the design
The new design uses the Command-Query Separation pattern by using two separate modules: one for the collecting of events and one for displaying statistics. The event collecting system achieves asynchronousity, scalability, and durability by using Scribe. The backend persistence and statistics module achieves all goals by using Cassandra and Thrift. As an extension of the <a href="http://highscalability.com/blog/2009/10/13/why-are-facebook-digg-and-twitter-so-hard-to-scale.html">push-on-change</a> model: the event collection stores denormalised data and it is later aggregated and normalised to the views the statistics module requires; we use MapReduce jobs within a Hadoop cluster.

<img src="/images/2015-04-27-finding-gold-in-big-data/event-collection-overview.png" alt="Event Statistics Overview">

## the statistics module
At FINN all our modular architecture is built either upon REST or interfaces defined by Apache Thrift. We discourage direct database access from our front-end applications. So our Statistics module simply exposes the aggregated read-optimised data out of cassandra, with a Thrift IDL like:
<pre style="font-size: 80%;">
service AdViewingsService {
    /** returns total viewings for a given adId */
    long fetchTotal(1: i64 adId)
    /** returns a list of viewings for the intervals between startTimestamp and endTimestamp **/
    list&lt;long> fetchRolled(1: i64 adId, 2: Interval interval, 3: i64 startTimestamp, 4: i64 endTimestamp)
}
enum Interval { YEAR, MONTH, DAY, HOUR, QUARTER_HOUR }
</pre>
<br/>

## the event collection module
 The collecting of events we wanted to happen asynchronously and in a fail-over safe manner so we chose a combination of thrift and <a href="https://github.com/facebook/scribe">Scribe</a> from facebook. Each event object, or bean, is a thrift defined object, and these are serialised using thrift into base64 encoded strings and transported through the network via Scribe. The event collection module is nothing more than a scribe sink and it dumps these thrift beans, still serialised, directly into <a href="http://cassandra.apache.org">Cassandra</a>.

Each event is schemaless in its <code>values</code> field, it's up to the application to decide what data to record, and is defined by thrift like:
<pre style="font-size: 80%;">struct Event {
    /** different categories generally won't be mixed in the normalised views. */
    1: required string category;
    2: required string subcategory;
    3: required map&lt;string,string> values;
}</pre>

For the persistence of events in cassandra we store events in rows based on the timestamp of the current minute plus a random number upto the numbers of nodes in our cassandra cluster. So a partition key looks like <code>&lt;minute-timestamp>_&lt;node-number></code>. The reason for the addition column "node_number", named "partition" in the cql, is it ensures write and read load is distributed around the cluster at all times, rather than one node being a hotspot for any given minute. Then each event is stored within a clustering key "collected_timestamp". The value columns are essentially the category, the subcategory, and the json map keys_and_values. 
<pre style="font-size: 80%;">
CREATE TABLE events (
  minute text,
  partition int
  type text,
  collected_timestamp timeuuid,
  collected_minute bigint,
  subcategory text,
  keys_and_values text,
  PRIMARY KEY ((minute, partition), type, collected_timestamp)
);
CREATE INDEX collected_minuteIndex ON events (collected_minute);
</pre>
We have moved the category column in as the first clustering key as the aggregation jobs typically only scan one type of category at a time. In hindsight we might not have done this as it would be better to be able to use the DateTieredCompactionStrategy. Within this schema there are two timestamp that we have to work with, both the real_timestamp representing when the event happened and the collected_timestamp when the event got stored in Cassandra. Analytical jobs, like how many bicycles were sold in Oslo on a specific day, are interested in the real_timestamp. While the incremental aggregation jobs are interested in aggregating just those events that have come into the system since the last incremental run.


<br/>
## the technologies <span class="image-wrap" style="float: left"><img style="margin: 5px; border: 0px solid black" src="http://avatar.identi.ca/8594-96-20100330175539.jpeg" alt="" />&nbsp;</span></h6>
Cassandra is truly amazing and refreshingly modern database: linear scalability, decentralised, elastic, fault-tolerant, durable; with a rich datamodel that provides often <a href="http://maxgrinev.com/2010/07/12/do-you-really-need-sql-to-do-it-all-in-cassandra/">superior</a> approaches to joins, grouping, and ordering than traditional sql. The counting module, the scribe sink, simply <a href="http://wiki.apache.org/cassandra/ScribeToCassandra">dumps</a> the thrift objects, without deserialising them, directly into a cassandra column family. We use Apache <a href="http://hadoop.apache.org">Hadoop</a> to then in the background aggregate this denormalised data. The storing of denormalised data in this manner extends the <a href="http://highscalability.com/blog/2009/10/13/why-are-facebook-digg-and-twitter-so-hard-to-scale.html">push-on-change</a> model, an approach far more scalable in “comparison with pull-on-demand model where data is stored normalized and combined by queries on demand – classical relational approach”<a href="http://maxgrinev.com/2010/07/12/do-you-really-need-sql-to-do-it-all-in-cassandra/">²</a>. In hadoop our aggregation jobs piece-wise over time scan over the denormalised data, normalising in this case by each ad's unique identifier, this normalised summation for each ad is then added to a separate column family in cassandra which uses counter columns and is optimised for query performance. 

Painting a wonderful picture, hopefully you can see it puts us one foot into an entirely new world of opportunity, but it would be a lie not to say the journey here has also come with its fair share of pain and anguish. The four key technologies we adopted here: cassandra, hadoop, scribe, and thrift; involve changes in the way we code and design.

Cassandra, being a <a href="http://www.slideshare.net/jericevans/cassandra-not-just-nosql-its-mosql">noSQL</a> database, or "Not only SQL", takes some investing in to understand how datamodels are designed to suit queries instead of writes. We also been bitten by an unfortunate bug or two along the way. The first was immediately after the 1.0 release when compression was introduced. We jumped on this a little too quickly also modifying its default settings for chunk_length_kb to suit out skinny rows for the denormalised data. This hit a <a href="https://issues.apache.org/jira/browse/CASSANDRA-3427">bug</a> leading to excessive memory usage bringing cassandra nodes down. The cassandra community was very quickly to the rescue and we were running again with hours. The second bug was again related to skinny rows. Each of our count objects were stored in individual rows resulting in a column family with billions of rows stored on each physical machine. This eventually <a href="http://thread.gmane.org/gmane.comp.db.cassandra.user/24052">blew</a> up in our face and the <a href="http://thread.gmane.org/gmane.comp.db.cassandra.user/24052">fix</a> was to disable bloom filters. It's obvious to us now that skinny rows should be avoided.

Hadoop is a beast of a monster, and despite bringing functional programming to a world of its own (don't go thinking you anything special because you're tinkering around with Scala) in many ways much of the joy Cassandra gave us was undermined by having to understand what the heck was going on sometimes in Hadoop. We were also using Hadoop in a slightly unusual way trying to run small incremental jobs on it with as high frequency as possible. This time-based functional programming is now better tackled with various new tools in the hadoop ecosystem like Spark or Storm. We also had plenty of headaches due to hadoop's centralised setup. Our cassandra and hadoop nodes co-existed on the same servers in the one cluster, providing data-locality – a key ingredient to good hadoop performance. But these servers came with a faulty raid controller locking up the servers multiple times each day for the past year (HP took over a year "looking" into the problem). For our cassandra cluster it successfully proved how rock solid fault-tolerance it really has – just imagine the crisis to the company if your monster relational database was crashing from faulty hardware twice a day. <span class="image-wrap" style="float: right"></span>But one of these faulty server was running the hadoop masters: namenode and jobtracker; so aggregation jobs were frequently crashing. Since then we're moved these services to a small separate virtual server, together they use no more than 600mb memory.

## the camel's back broken
We were using Thrift throughout our modular platform so we'd mostly broken the camel's back there. But Scribe has given us plenty of problems. Like most code open sourced from facebook it seems to be "code thrown over the wall". It contains traces of private facebook code, has very little logging+documentation+support, and has settings that rarely work without peculiar and exact combinations with other settings. We believed scribe to be fault tolerant when it simply wasn't and we're lost many counts when resending buffers after a downtime or pause period. We have since moved to Kafka, as our general messaging bus for all event driven design without our microservices platform.

These problems have all since been addressed – they are but part of our story. Dealing with each new technology on its own was no big deal but a strong recommendation in hindsight is not to trial multiple new technologies in the one project. The reputation of that project could well fall to that of its weakest link.

Of all these technologies it is Cassandra that has proved the most successful, and today is the essential and fundamental technology to our "big data" capabiilities.

On top of this platform we've come a long way. Today we use predeominantly Spark over Hadoop, although more and more use-cases are only requiring Cassandra. Some of these examples are messaging inboxes for each user, storing users search history, fraud detection, mapping probabilities of ipaddresses to geographical locations, and providing collaborative filtering using both Spark's ALS algorithm and Mahout as a delivery framework.

