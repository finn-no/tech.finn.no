---
authors: mick
comments: true
date: 2010-11-04 18:11:57+00:00
layout: post
title: Finding Gold in Big Data
---
In the <a href="/foraging-in-the-landscape-of-big-data/">previous article</a> we introduced our own introduction into the world of Big Data, and explored what it meant for FINN. Here we'll go into the technical depth about the implementation of our Big Data needs.

## rehashing the previous article
FINN is a busy site, the busiest in Norway, and we display ~50 million ad pages each day. The old system responsible for collecting statistics was performing up to a thousand database writes per second in peak traffic. Like a lot of web applications we had a modern scalable presentation and logic tier based upon ten tomcat servers but just one not-so-scalable monster database sitting in the data tier. The sybase procedure responsible for writing to the statistics table was our biggest thorn.

At the time we were in the process of modularising the FINN web application. The time was right to turn our statistics system into something modern and modular. We wanted an asynchronous, fault-tolerance, linearly scaling, and durable solution. The new design uses the Command-Query Separation pattern by using two separate modules: one for the collecting of events and one for displaying statistics. The event collecting system achieves asynchronousity, scalability, and durability by using Scribe. The backend persistence and statistics module achieves all goals by using Cassandra and Thrift. As an extension of the <a href="http://highscalability.com/blog/2009/10/13/why-are-facebook-digg-and-twitter-so-hard-to-scale.html">push-on-change</a> model: the event collection stores denormalised data and it is later aggregated and normalised to the view the statistics module requires; we use MapReduce jobs within a Hadoop cluster.

<img style="margin: 10px; border: 0px solid black" src="/wp-content/uploads/2012/08/Simple-overview.png" width="600px" alt="Simple overview" />

## the statatistics module
At FINN all our modular architecture is built upon interfaces defined by Apache Thrift, and we discourage direct database access from our front-end applications. So our Statistics module simply exposes the read-optimised data sitting in cassandra, its interface defined by thrift like:
<pre style="font-size: 80%;">
service AdViewingsService {
    /** returns total viewings for a given adId */
    long fetchTotal(1: i64 adId)
    /** returns a list of viewings for the intervals between startTimestamp and endTimestamp **/
    list&gt;long> fetchRolled(1: i64 adId, 2: Interval interval, 3: i64 startTimestamp, 4: i64 endTimestamp)
}
enum Interval { YEAR, MONTH, DAY, HOUR, QUARTER_HOUR }
</pre>
<br/>

<h6>the event collection module</h6>
 The collecting of events we wanted to happen asynchronously and in a fail-over safe manner so we chose a combination of thrift and <a href="https://github.com/facebook/scribe">Scribe</a> from facebook. Each event object, or bean, is a thrift defined object, and these are serialised using thrift into base64 encoded strings and transported through the network via Scribe. The event collection module is nothing more than a scribe sink and it dumps these thrift beans, still serialised, directly into <a href="http://cassandra.apache.org">Cassandra</a>. 

Each event is schemaless in its {{values}} field, it's up to the application to decide what data to record, and is defined by thrift like:
<pre style="font-size: 80%;">struct Event {
    /** different categories generally won't be mixed in the normalised views. */
    1: required string category;
    2: required map<string, string> values;
}</pre>

For the persistence of events in cassandra we store events in rows based on the timestamp of the current minute plus a random number upto the numbers of nodes in our cassandra cluster. So a row key looks like {{<minute-timestamp>_<node-number>}}. The reason for the node_number suffix is it ensures a random write and read load around the cluster at all times, we didn't want one node to be the hotspot for any given minute. Column are composite composed of the full timestamp for the event and the category. We put the category in as the first element in the composite column name as aggregation jobs typically only scan one type of category at a time. In addition to the columns holding the serialised thrift events we have one additional column, marked as a secondary index, that holds the <minute-timestamp> value. This additional secondary index is required since 

<img src="/wp-content/uploads/2012/12/event-collection-schema.png"/>

<br/>
<h6>the technologies</h6>
Cassandra is truly amazing modern database: linear scalability, decentralised, elastic, fault-tolerant, durable; with a rich datamodel that provides often <a href="http://maxgrinev.com/2010/07/12/do-you-really-need-sql-to-do-it-all-in-cassandra/">superior</a> approaches to joins, grouping, and ordering than traditional sql. The counting module, the scribe sink, simply <a href="http://wiki.apache.org/cassandra/ScribeToCassandra">dumps</a> the thrift objects, without deserialising them, directly into a cassandra column family. We use Apache <a href="http://hadoop.apache.org">Hadoop</a> to then in the background aggregate this denormalised data. The storing of denormalised data in this manner extends the <a href="http://highscalability.com/blog/2009/10/13/why-are-facebook-digg-and-twitter-so-hard-to-scale.html">push-on-change</a> model, an approach far more scalable in “comparison with pull-on-demand model where data is stored normalized and combined by queries on demand – classical relational approach”<a href="http://maxgrinev.com/2010/07/12/do-you-really-need-sql-to-do-it-all-in-cassandra/">²</a>. In hadoop our aggregation jobs piece-wise over time scan over the denormalised data, normalising in this case by each ad's unique identifier, this normalised summation for each ad is then added to a separate column family in cassandra which uses counter columns and is optimised for query performance. We'll go into all of this in more detail in a later article.

This paints a wonderful picture, hopefully you can see it puts us one foot into an entirely new world of opportunity, and we can only hope the product development teams really take advantage of it for the sake of our users, but it would be a lie not to say the journey here has also come with its fair share of pain and anguish. The four key technologies we adopted here: cassandra, hadoop, scribe, and thrift; are all fairly-without the same stability, maturity, and documentation compared to those traditional databases having been around for decades.

Cassandra, being a <a href="http://www.slideshare.net/jericevans/cassandra-not-just-nosql-its-mosql">moSQL</a> database, <span class="image-wrap" style="float: left"><img style="margin: 5px; border: 0px solid black" src="http://avatar.identi.ca/8594-96-20100330175539.jpeg" alt="" />&nbsp;</span>takes some investing in to understand how datamodels are designed to suit queries instead of writes. We also got bitten by two unfortunate bugs. The first was immediately after the 1.0 release when compression was introduced. We jumped on this a little too quickly also modifying its default settings for chunk_length_kb to suit out skinny rows for the denormalised data. (Such skinny rows we'll discuss later we don't recommend). This hit a <a href="https://issues.apache.org/jira/browse/CASSANDRA-3427">bug</a> leading to massive memory usage bringing every cassandra node down. The cassandra community was very quickly to the rescue and we were running again soon enough. The second bug was again related to skinny rows. Each of our count objects were stored in individual rows resulting in a column family with billions of rows stored on each physical machine. This eventually <a href="http://thread.gmane.org/gmane.comp.db.cassandra.user/24052">blew</a> in our face and the <a href="http://thread.gmane.org/gmane.comp.db.cassandra.user/24052">fix</a> was to disable bloom filters.

Hadoop is a beast of a monster, and despite bringing functional programming to a world of its own (don't go thinking you anything special because you're tinkering around with Scala) in many ways much of the joy Cassandra gave us was undermined by having to understand what the heck was going on sometimes in Hadoop. We were also using Hadoop in a slightly unusual way trying to run small incremental jobs on it with as high frequency as possible. This time-based functional programming is now better tackled with various new tools in the hadoop ecosystem like Storm. We also had plenty of headaches due to hadoop centralised setup. Our cassandra and hadoop nodes co-exist on the same servers in the one cluster, providing data-locality – a key ingredient to good hadoop performance. But these servers came with a faulty raid controller locking up the servers multiple times each day for the past year (HP is still looking into it). For our cassandra cluster it successfully proved how rock solid fault-tolerance it really has – just imagine the crisis to the company if our monster sybase database was crashing from faulty hardware every day. <span class="image-wrap" style="float: right"><img style="margin-left: 10px; border: 0px solid black" src="http://hash-table.com/home/wp-content/uploads/2012/07/hadoop3-42x42.jpg" alt="" />&nbsp;</span>But one of these faulty server was running the hadoop masters: namenode and jobtracker; so aggregation jobs were frequently crashing. Since then we're moved these services to a small separate virtual server, together they use no more than 600mb memory.

<span class="image-wrap" style="float: left"><br/><img style="margin: 5px; border: 0px solid black" src="/wp-content/uploads/2012/08/Scribe-Facebook-thumb.png" alt="" /><br/><br/><br/></span>We were using Thrift throughout our modular platform so we'd mostly broken the camel's back there. But Scribe has given us the most problems. Like most code open sourced from facebook it seems to be "code thrown over the wall". It contains traces of private facebook code, has very little logging+documentation+support, and has settings that rarely work without peculiar and exact combinations with other settings. We believed scribe ti be fault tolerant when it simply wasn't [FIXME: reference] and we're lost many counts when it is resending buffers after a downtime period. An alternative to scribe would be Flume.

These problems have all since been addressed – they are but part of our story. Dealing with each new technology on its own was no big deal but a strong recommendation in hindsight is not to trial multiple new technologies in the one project. The reputation of that project could well fall to that of its weakest link.

A number of challenges lie ahead for us. Technically we want to: 
&nbsp;&nbsp;&nbsp; • move away from skinny</a> rows, bundling all counts for every 15 minutes into single rows, 
&nbsp;&nbsp;&nbsp; • replace super columns with composite columns,
&nbsp;&nbsp;&nbsp; • change from an ordered partitioner to a random partitioner,
&nbsp;&nbsp;&nbsp; • take advantage of custom row caching,
&nbsp;&nbsp;&nbsp; • implement anomaly detection to remove tracking vandalism, and
&nbsp;&nbsp;&nbsp; • install Hive to provide better company-wide integration.
