---
layout: post
comments: true
date: 2015-06-05 22:47:32+0200
authors: Per Jørgen Walstrøm
title: "When flying from Oslo to London, you don't want to go via Moscow"
tags:
- meta-search
- flight-search
---
If you [want to travel from Oslo to London](http://www.finn.no/reise/flybilletter/resultat?tripType=roundtrip&requestedOrigin=OSL.METROPOLITAN_AREA&requestedDestination=LON.METROPOLITAN_AREA&requestedOrigin2=&requestedDestination2=&requestedDepartureDate=11.11.2015&requestedReturnDate=13.11.2015&numberOfAdults=1&numberOfChildren=0&cabinType=economy), you may end up getting results which include layovers in far-away places. Do you really want to spend a night at the airport in Moscow if you only want to go from Oslo to London? Probably not, but nonetheless, some of the results we receive at the [FINN Flight Search](http://www.finn.no/reise/flybilletter/) are those kind of travels. Those travels may be really cheap, ending up at the top of our result list as we sort by price, but they are just garbage and we do not want our users to be confused by those outliers. We want them to go away!

##A theoretical solution
The correct way to remove the outliers is by [finding the quartiles](http://en.wikipedia.org/wiki/Quartile). When the quartiles are found, the upper fence is defined by 
Upper fence = Q1 + 1.5 * IQR,
where IQR is the Interquartile Range; Q3 - Q1.
We are using Solr at FINN, but the stats query in Solr does not give us the quartiles out of the box, so we would need to do a separate query to calculate the quartiles. That would be too expensive, so we have abandoned that idea. We need a more practical solution.

##A previous solution