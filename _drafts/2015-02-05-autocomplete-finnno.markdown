---
authors: [fmr, chriswk]
comments: true
date: 2015-02-05 13:13:08+0100
layout: post
title: "Autocomplete @FINN.no"
description: "How we do autocomplete when searching FINN.no"
tags: ["search", "autocomplete", "solr"]
---

To help users find items quickly we have introduced different levels of auto-completetion at FINN.no.  There are currently three related concepts which will be described in detail in the post. The idea behind these are to reduce navigation through different result pages or verticals and to lead the users as quickly as possible to the relevant items.

At FINN.no we use one autocomplete index for all markets.  We then use SOLR's filterquery to filter out just the relevant suggestions. (see searchkeygroups in the example below).


# Autocomplete
![BAP AutoComplete](/images/autocomplete-finnno/bap_ac.png "BAP AutoComplete")

## Frontend
For our desktop frontend we use [jQuery UI](http://jqueryui.com/autocomplete/)'s autocomplete component. Our mobile site is using its own custom script.

## Backend

### SOLR
Our autocomplete SOLR backend is inspired by:  [Cominvent's blogpost](http://www.cominvent.com/2012/01/25/super-flexible-autocomplete-with-solr/) on the topic, which in turn is built on top of [Lucidworks' post](http://lucidworks.com/blog/auto-suggest-from-popular-queries-using-edgengrams/).  We stripped it down to include the following fields:

* id: unique identificator - typically generated
* suggest: the term/item in question
* type: (multivalued) reflects sources (see below)
* rank: used to order items (see boostfunction `bf` in requesthandler below)
* boosting_word: used in multiword queries to boost typical phrases - e.g. boost entries with boosting_word='audi' for query='a4'.
* searchkeygroups: (multivalued) reflects which market(s) the item is relevant for

The field `suggest` is copied (using SOLR's [copyfield](https://cwiki.apache.org/confluence/display/solr/Copying+Fields)) to the following non-stored fields used for matching (`qf`):

* exact: not used at the moment, but intended to boost exact matches - the item with suggest='audi a6' should be the first entry when query='audi a6'
* suggest_textnge: builds n-grams based on suggest from left edge of word. e.g. suggest='audi' has tokens: 'a', 'au', 'aud', 'audi' and matches all these when entered as query. boosted over matches in `suggest_textng` in `qf` in requesthandler below.
* suggest_textng: builds n-grams based on suggest. e.g. suggest='audi' has tokens: 'a', 'u', 'd', 'i', 'au', 'ud', ... and matches all these when entered as query.

example data:
{% highlight bash %}
searchkeygroups,exact,rank,id,suggest,type,boosting_word
CAR,A4,114,CAR_A4_Audi,A4,MODEL,Audi
BAP,4s,152,d3a3f051-fa0c-4c25-b017-c732b5dbbe10_0,4s,QUERY_EXT,iphone
CAR,Audi A4,134,CAR_MAKE_MODEL_A4_Audi,Audi A4,MAKE_MODEL,
CAR,Audi A6,124,CAR_MAKE_MODEL_A6_Audi,Audi A6,MAKE_MODEL,
BAP,audi,115,f64ad500-6d7f-4704-8c76-26130bba60e7,audi,QUERY,
CAR,Audi A3,113,CAR_MAKE_MODEL_A3_Audi,Audi A3,MAKE_MODEL,
{% endhighlight %}

The two posts mentioned above describes our technical implementation quite well, with the following exceptions:

* We have stripped down the number of fields used.
* Simplified rank-calculation (the value in the index determines the order of suggestions)
* Added searchkeygroups to distinguish different markets

### SOLR Requesthandler
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

### SOLR fields (from `schema.xml`)
{% highlight xml %}
<fields>
    <!-- AutoComplete fields
    Construct documents containing these fields for all suggestions you like to provide
    Then use a dismax query to search on some fields, display some fields and boost others
    -->
    <field name="id" type="string" indexed="true" stored="true" required="true"/>
    <dynamicField name="*_textnge" type="autocomplete_edge" indexed="true" stored="false"/>
    <!-- A variant of * which matches from the left edge of all terms (implicit truncation) -->
    <dynamicField name="*_textng" type="autocomplete_ngram" indexed="true" stored="false" omitNorms="true"/>

    <!-- Suggest field -->
    <field name="suggest" type="text_suggest" indexed="true" stored="true" required="true" omitNorms="true"/>
    <field name="type" type="string" indexed="true" stored="true" multiValued="true"/>
    <field name="exact" type="exact_match" indexed="true" stored="true" multiValued="false"/>


    <!-- Add an ngram edge and an ngram suggest copy -->
    <copyField source="suggest" dest="suggest_textnge"/>
    <copyField source="suggest" dest="suggest_textng"/>

    <!--Field for exact matches (not tokenized)-->
    <copyField source="suggest" dest="exact"/>

    <!-- Helper fields for boost functions -->
    <field name="searchkeygroups" type="string" indexed="true" stored="true" multiValued="true"/>
    <field name="boosting_word" type="keywordField" indexed="true" stored="true"/>
    <field name="rank" type="long" indexed="true" stored="true"/>

</fields>
{% endhighlight %}

### Java
Our backend is a java application.  It provides a single endpoint with two interesting parameters: `term` and `displayHits`.  The first is the term the user has entered, the latter is whether to include the number of actual hits from the live index.

Example SOLR queries:

`term=aud` on the used car frontpage results in the following SOLR query to the autocomplete index

```
q=aud&
fl=score,heading,imageurl,rank,publisheddate,suggest,id,type,boosting_word&
fq=searchkeygroups:ALL+OR+searchkeygroups:NORWEGIAN+OR+searchkeygroups:SEARCH_ID_CAR_USED&
sort=score+desc&
rows=30&
```
`term=audi a6` on the used car frontpage results in the following SOLR query to the autocomplete index boosting entries 

qq=boosting_word:audi&q=a6+-audi+&qt=dismax&fl=score,heading,imageurl,rank,publisheddate,suggest,id,type,boosting_word&boost=query($qq,1)&fq=searchkeygroups:CAR+OR+searchkeygroups:NORWEGIAN+OR+searchkeygroups:SEARCH_ID_CAR_USED&sort=score+desc&rows=30

In our live market index we have a dedicated field `autocomplete_text` which we use to show the number of hits in the corresponding index.  We optimize the query by setting `rows=0` which gives the following SOLR query:

```
facet.query={!edismax qf='autocomplete_text'}a4&
facet.query={!edismax qf='autocomplete_text'}a6&
facet.query={!edismax qf='autocomplete_text'}audi a4&
facet.query={!edismax qf='autocomplete_text'}audi a6&
qt=dismax&
fq=searchkey:SEARCH_ID_CAR_USED&
rows=0&
facet=true
```

doing two searches to get number of hits
servercomponent

### Data sources
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
