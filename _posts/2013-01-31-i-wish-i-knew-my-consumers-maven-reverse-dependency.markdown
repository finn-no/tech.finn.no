---
authors: roar
comments: true
date: 2013-01-31 12:49:05+00:00
layout: post
title: I wish I knew my consumers - Maven Reverse Dependency
redirect_from: /i-wish-i-knew-my-consumers-maven-reverse-dependency/
---

At FINN.no being a developer fixing bugs in a library is a breeze. Getting every user of your library to use the fix, however, is a different story. How to know who to notify? I mean, I know my library's dependencies, but who "out there" has dependency to the component where I just fixed a bug? I wish. Enter maven-dependency-graph.

The idea was born on the plane back home from a Copenhagen hosted conference. Graph database. Download neo4j and start dabbling at a maven plugin. Flying time Copenhagen - Oslo was too short, all of a sudden.

From there, the idea slept for a couple of years. Until the need arose somewhere among the developers. With 100+ different applications running with common core services and libraries, everybody suddenly needed to know who depended on their code which had recently been bugfixed. So the old idea was dusted off and once more saw the light of day. We needed to upgrade the server installation and the API to neo4j - which took some time to grasp; but after some playing around, it became obvious and easy.

The idea was to have every project report its dependencies to a graph database, building the tree of dependencies on each commit. This constitutes one half of the plugin. Over time, all projects will have reported their dependencies, and from there on part two of the plugin comes into use. It will examine the reverse dependencies to the *current* maven project, and report all incoming dependencies to it in the maven log. Hey, presto! We now know who out there uses us! And even which version they are using, thanks to two different keys into the built-in lucene index engine.

The plugin is published on github @ [Finn Technology's account](https://github.com/finn-no/maven-dependency-mapper). Feel free!
[@gardleopard](http://twitter.com/gardleopard) and [@roarjoh](http://twitter.com/roarjoh)

## Usage examples

Dependencies to current maven project:
`
mvn no.finntech:dependency-mapper-maven-plugin:read
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building greenpages thrift-client 3.4.5-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- dependency-mapper-maven-plugin:1.0-SNAPSHOT:read (default-cli) @ commons-thrift-client ---
[INFO] Resolving reverse dependencies
[INFO] no.finntech.travel.supplier:supplier-client:1.2-SNAPSHOT -> no.finntech:commons-thrift-client:3.1.1
[INFO] no.finntech.cop:client:1.1-SNAPSHOT -> no.finntech:commons-thrift-client:3.1.1
[INFO] no.finntech.oppdrag-services:iad-model:2013.2-SNAPSHOT -> no.finntech:commons-thrift-client:3.4.3
[INFO] no.finntech:minfinn:2013.2-SNAPSHOT -> no.finntech:commons-thrift-client:3.4.3
[INFO] no.finntech:service-user:2013.2-SNAPSHOT -> no.finntech:commons-thrift-client:3.4.3
[INFO] no.finntech:service-oppdrag:2013.2-SNAPSHOT -> no.finntech:commons-thrift-client:3.4.3
[INFO] no.finntech:kernel:2013.2-SNAPSHOT -> no.finntech:commons-thrift-client:3.4.3
`
(umpteen lines skipped...)
`
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.957s
[INFO] Finished at: Thu Jan 31 09:50:19 CET 2013
[INFO] Final Memory: 9M/211M
[INFO] ------------------------------------------------------------------------
`

Usage of third party framework (using neo4j's included admin interface):
![Noe4jshot](/images/2013-01-31-i-wish-i-knew-my-consumers-maven-reverse-dependency/neo4jshot.png)
