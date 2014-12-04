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

# Autocomplete in FINN.no

To help users find items quickly we have introduced different levels of auto-completetion at FINN.no.  There are currently three related concepts which will be described in detail in the post. The idea behind these are to reduce navigation through diffetrent result pages or verticals and to lead the users as quickly as possible to the relevant items.

At FINN.no we have a [SOLR](http://lucene.apache.org/solr/) index for each market (or vertical), with their own `schema.xml` and `solrconfig.xml`, however they contain ads from different sub-markets - for instance both cars and boats are in the MOTOR index.

markets:
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


@FINN: one index pr adtype/vertcal - i.e. BAP has one index, MOTOR has another.
@FINN: several searchkeys pr index - i.e. MC & CARS are both indexed in MOTOR, but has different searchkeys

## Autocomplete
heavily inspired by: https://github.com/cominvent/autocomplete
removed fields not in use
added rank 
added searchkeys (BAP, MC, CAR etc)
either search inside type OR across, but coupling nth word with n-1
** screendump: finn torget autocomplete
java-code
doing two searches to get number of hits
### sources
We have many different sources for the dis


 (query-logs, index): http://solr01.finn.no:12910/solr/autocomplete/select?wt=json&indent=true&fl=*&facet=true&facet.field=type&rows=0
 - Someone should keep these up to date on a monthly basis
 - seasonal trends: boost lawnmower in spring and skis in winter
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
