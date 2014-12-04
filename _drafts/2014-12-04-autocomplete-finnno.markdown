---
authors:
- frerodla
comments: true
date: 2014-12-04 13:13:08+0100
layout: post
title: "Autocomplete @FINN.no"
slug: autocomplete-finnno
description: ""
category: 
tags: []
---

To help users find items quickly we have introduced different levels of auto-completetion at FINN.no.  There are currently three related concepts which will be described in detail in the post. The idea behind these are to reduce navigation through diffetrent result pages or verticals and to lead the users as quickly as possible to the relevant items.

At FINN.no we have a [SOLR](http://lucene.apache.org/solr/) index for each market (or vertical), with their own `schema.xml` and `solrconfig.xml`, however they contain ads from different sub-markets - for instance both cars and boats are in the MOTOR index.


**TODO beskriv SKGrouops, vertical, market**
verticals:
    CAR("Bil"),
    BAP("Torget"),
    BAP_WEBSTORE("Torget"),
    BOAT("Båt"),
    REALESTATE("Eiendom"),
    JOB("Jobb"),
    B2B("Næring"),
    TRAVEL("Reise"),
    MC("MC"),
    GLOBAL("Hele FINN");
skg:
	CAR, BAP, BAP_WEBSTORE, BOAT, REALESTATE, JOB, B2B, TRAVEL, MC, ALL, NORWEGIAN, ABROAD, GLOBAL;

@FINN: one index pr adtype/vertcal - i.e. BAP has one index, MOTOR has another.
@FINN: several searchkeys pr index - i.e. MC & CARS are both indexed in MOTOR, but has different searchkeys

## Autocomplete
![BAP AutoComplete](/images/2014-12-04-autocomplete-finnno/bap_ac.png "BAP AutoComplete")

// TODO intro

### Frontend
For our desktop frontend we use [jQuery UI](http://jqueryui.com/autocomplete/)'s autocomplete component. Our mobile site is using its own custom script.

### Backend

#### SOLR
Our autocomplete SOLR backend is inspired by:  [Cominvent's blogpost](http://www.cominvent.com/2012/01/25/super-flexible-autocomplete-with-solr/) on the topic, which in turn is built on top of [Lucidworks' post](http://lucidworks.com/blog/auto-suggest-from-popular-queries-using-edgengrams/).  We stripped it down to include the following fields:

* id: unique identificator - typically genrated
* suggest: the term/item in question (the term),
* type: (multivalued) reflects sources (see below)
* rank: used to order items (see `bf` in requasthandler below
* boosting_word: used in multiword queries to boost typical phrases - e.g. boost entries with boosting_word='audi' for query='a4'.
* searchkeygroups: (multivalued) reflects which market(s) the item is relevant for

The field `suggest`): is copied to the following non-stored fields used for matching (`qf`):

* exact: not used at the moment, but intened to boost exact matches - the item with suggest='audi a6' should be the first entry when query='audi a6'
* suggest_textnge: builds n-grams based on suggest from left edge of word. e.g. suggest='audi' has tokens: 'a', 'au', 'aud', 'audi' and matches all these when entered as query. boosted over matches in `suggest_textng` in `qf` in requesthandler below.
* suggest_textng: builds n-grams based on suggest. e.g. suggest='audi' has tokens: 'a', 'u', 'd', 'i', 'au', 'ud', ... and matches all these when entered as query.

For implementation-details we recommend reading the two posts mentioned above.

#### SOLR Requesthandler

{% highlight xml %}
<requestHandler class="solr.SearchHandler" name="dismax" default="true" >
  <lst name="defaults">
    <str name="defType">edismax</str>
    <str name="rows">10</str>
    <str name="fl">suggest,rank,searchkeygroups,score</str>
    <str name="q.alt">*:*</str>
    <str name="qf">suggest^15 suggest_textnge^13 suggest_textng^8</str>
    <str name="bf">product(sum(rank,1))^20</str>
    <str name="debugQuery">false</str>
  </lst>
</requestHandler>
{% endhighlight %}

removed fields not in use
added rank 
added searchkeys (BAP, MC, CAR etc)
either search inside type OR across, but coupling nth word with n-1

#### Java
doing two searches to get number of hits
servercomponent

### sources
Each market have their own set of autocomplete items and sources in adition to common ones.  Keeping the items up to date is vital for good autocomplete suggestions. Someone should do this on a monthly basis, keeping in mind:
* new products: new iPhones, etc
* change in market: new jobs titles: e.g. "Social Media expert"
* seasonal trends: boost lawnmower in spring and skis in winter

Some sources used at FINN inlcude:
* Company names: Includes a curated list of companies which have active ads.
* Job titles: Includes a curated list of job titles used inn ads on FINN, intersected with [Statistics Norway](http://www.ssb.no/a/yrke/)'s offical list of Job titles.
* List of car dealers registered with FINN
* [FINN kart](http://kart.finn.no)'s geo-names
* Curated list of words extracted from our query-logs for the respective markets.
* Make and models from cars, boats, MCs, electronics etc
* Categories used throughout the respective markets.
* Boat types
* Manual list of words


 (query-logs, index): http://solr01.finn.no:12910/solr/autocomplete/select?wt=json&indent=true&fl=*&facet=true&facet.field=type&rows=0
search filters on SKGroups (ex: JOB => JOB, NORWEGIAN, ALL, SEARCH_ID_JOB_FULLTIME) so we can index autocompleteitems on any of these levels to get them included.


http://solr01.finn.no:12910/solr/autocomplete/select?q=*%3A*&rows=0&wt=json&indent=true&facet=true&facet.field=searchkeygroups
"SEARCH_ID_JOB_FULLTIME",15002,
"JOB_TITLE",8155,
"CAR",4082,
"BAP",2733,
"NORWEGIAN",2136,
"JOB_GEO",2135,
"SEARCH_ID_JOB_MANAGEMENT",963,
"BOAT",773,
"SEARCH_ID_JOB_PARTTIME",215,
"MC",160,
"REALESTATE",18,




http://solr01.finn.no:12910/solr/autocomplete/select?q=*%3A*&rows=4&wt=json&indent=true&facet=true&facet.field=searchkeygroups
{suggest: "iphone",rank: 194,searchkeygroups: ["BAP"],score: 194.80666}

## Odin
SOLR shard over multiple indexes
using FINNs search-api - i.e., no json endpoint.
using solr's grouping on searchkey - i.e. really gives you a list of serchkeys, with N docs
computes an order for the results based on a number og stages/ideas = sortalgorithm
example of stages: hitsdivider, solrscore, static boost, 
** screendump: m.finn odin
** screendump: odindebug

## Search-front
New application with endpoints for:
- autocomplete
- other search-stuff needing external (json) endpoint 
- new front-endpoint (2015)
Inspired by many big players all having a leading searchfield on their site
Will typically contain a list (types) og list of elements (results)
API: term, user, location, types, searchKeys
** screendump: linkedin
** screendump: yammer
