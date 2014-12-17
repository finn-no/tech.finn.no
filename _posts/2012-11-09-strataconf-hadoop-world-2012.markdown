---
authors: mick
comments: true
date: 2012-11-09 13:23:06+00:00
layout: post
title: StrataConf & Hadoop World 2012…
redirect_from: /strataconf-hadoop-world-2012/
tags:
- Big data
- cassandra
- hadoop
---

A summary of this year's Strataconf & Hadoop World.
A fascinating and inspiring conference with use-cases on both sides of an ethical divide – proof that the technologies coming are game-changers in both our industry and in society. Along with some intimidating use-cases i've never seen such recruitment [efforts](http://twitter.com/SQLDiva/status/261523933243789312) at any conference before, from multi-nationals to the [CIA](https://twitter.com/comsysto/status/261174163455225857). The need for developers and data scientists in Big Data is burning – the market for Apache Hadoop Market is [expected](https://twitter.com/TheASF/status/263823598731530240) to reach $14 billion by 2017.

Plenty of honesty towards the hype and the challenges involved too. A barcamp _Big Data Controversies_ labelled it all as Big Noise and looked at ways through the hype. It presented balancing perspectives from a insurance company's statistician who has dealt successfully with the problem of too much data for a decade and a hadoop techie who could provide much desired answers to previously impossible questions. Highlights from this barcamp were…

  * One should always use intelligent samples before ever committing to big data.


  * Unix tools can be used but they are not very fault tolerant.


  * You know when you're storing too much denormalised data when you're also getting high compression rates on it.


  * MapReduce isn't everything as it can be replaced with indexing.


  * If you try to throw automated algorithms at problems without any human intervention you're bound to bullshit.


  * Ops hate hadoop and this needs to change.


  * Respecting user privacy is important and requires a culture of honesty and common-sense within the company. But everyone needs to understand what's illegal and why.



**Noteworthy (10 minute) keynotes…**


  *
 _[The End of the Data Warehouse](http://bit.ly/SJGrHf )_. They are monuments to the old way of doing things: pretty packaging but failing to deliver the business value. But Hadoop too is still flawed…  Also a [blog](http://bit.ly/RijQBv) available.



  * _[Moneyball for New York City](http://bit.ly/R6bkFS)_. How NYC council started combining datasets from different departments with surprising results.



  * _[The Composite Database](http://bit.ly/Pwgj4y)_, a focus on using big data for product development. To an application programmer the concept of a database is moving from one entity into a componential architecture.



  * _[Bringing the 'So What' to Big Data](http://t.co/pmpdJw3h)_, a different keynote with a sell towards going to work for the CIA. Big data isn't about data but changing and improving lives.



  * _[Cloud, Mobile and Big Data](http://bit.ly/PPVB15)_. Paul Kent, a witty speaker, talks about analytics in a new world. "At the end of the day, we are closer to the beginning than we are at end of this big data revolution… One radical change hadoop and m/r brings is now we push the work to the data, instead of pulling the data out."

**Noteworthy (30 minute) presentations…**


  *
 _[The Future – Hadoop-2](http://slidesha.re/PAXdu2)_. Hadoop YARN makes all functional programming algorithms possible, reducing the existing map reduce framework to just one of many user-land libraries. Many existing limitations are removed. Fault-tolerance is improved (namenode). 2x performance improvement on small jobs.



  * _[Designing Hadoop for the Enterprise Data Center](http://tech.finn.no/?attachment_id=1758)_. A joint talk from Cisco and Cloudera on hardware tuning to meet Hadoop's serious demands. 10G networks help. dual-attached 1G networks is an alternative. More jobs in parallel will average out network bursts. Data-locality misses hurt network, consider above a 80% data-locality hitrate good.



  * _[How to Win Friends and Influence People](http://slidesha.re/YraGqG)_. LinkedIn presents four of their big data products
∘ Year in Review. Most successful email ever – 20% response rate.
∘ Network Updates.
∘ Skills and Endorsements. A combination of propensity to know someone and the propensity to have the skill.
∘ People You May Know. Listing is easy, but ranking required combining many datasets. 
All these products were written in PIG. Moving data around is the key problem. Kafka is used instead of scribe.



  * _[Designing for data-driven organisation](http://bit.ly/UaxOdm)_. Many companies who think they are data-driven are in fact metrics-driven. It's not the same thing. Metrics-driven companies often want interfaces with less data. Data-driven companies have data rich interfaces presenting holistic visualisations.



  * _[Visualizing Networks](http://slidesha.re/RzPWuk)_. The art of using the correct visualisation and layout. Be careful of our natural human trait to see visual implications from familiarity and proximity – we don't always look at the legend. A lot of examples using the d3 javascript library.

The two training sessions I attended were Testing Hadoop, and Hadoop using Hive.
_[Testing Hadoop.](http://bit.ly/XbVbDK)_
Presented by an old accomplice from the NetBeans Governance board, Tom Wheeler. He presented an interesting perspective on testing calling it another form of computer security: "a computer is secure if you can depend on it and its software to behave as you expect". Otherwise i took home a number of key technologies to fill in the gaps between and around our current unit and single-node integration tests on our CountStatistics project: Apache MRUnit for m/r units, MiniMRCluster and MiniDFSCluster for multi-jvm integration cluster, and BigTop for ecosystem testing (pig, hive, etc). We also went through various ways to benchmark hadoop jobs using TeraSort, MRBench, NNBench, TestDFSIO, GridMix3, and SWIM. Lastly we went through a demo of the free product "Cloudera Manager" – a diagnostics UI similar to Cassandra's OpsCenter.

Hadoop using Hive.
Hive provides an SQL interface to Hadoop. It works out-of-the-box if you're using HBase but with Cassandra as our underlying database we haven't gotten around to installing it yet. The tutorial went through many live queries on a AWS EC2 cluster, exploring the specifics to STRUCTs in schemas, JOINs, UDFs, and serdes. This is a crucial interface to make it easier for others, particularly BI and FINN økosystem, to explore freely through our data. Pig isn't the easiest way in for outsiders, but everyone knows enough SQL to get started. Fingers crossed we get Hive or Impala installed some time soon…  

A number of meet-ups occurred during the evenings, one hosted at AppNexus, a company providing 10 billion real-time ads per day (with a stunning office). AppNexus does all their hadoop work using python, but they also putting focus on RESTful Open APIs like we do. The other meetup represented Cassandra by [DataStax](http://www.datastax.com/) with plenty of free Cassandra beer. Latest [benchmarks](http://bit.ly/Ut7ZzB) prove it to be the fastest distributed database around. I was hoping to see more Cassandra at strataconf – when someone mentions big data i think of Cassandra before Hadoop.

Otherwise this US election was on the news as the _[big data election](http://bit.ly/RCFWim)_

![Hadoop](/images/2012-11-09-strataconf-hadoop-world-2012/hadoop1.jpg)