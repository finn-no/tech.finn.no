---
layout: post
comments: true
date: 2018-04-09 13:00:00+0100
authors: Henrik Falch
title: "Personalized search with a custom SOLR plugin"
tags:
- solr
- plugin
- recommendations
- search
- personalized search
---

## Background

On [FINN.no](https://www.finn.no) people can search for classified ads, where the backend system is using SOLR as the search engine. Default sorting on the vertical "torget" is by relevancy, which is based on SOLR score. The SOLR score for a document is again calculated from query relevance and the ad's published date. Here is an example searching for the word chair:

<figure>
    <img class="center-block" src="/images/2018-04-09-personalized-search/example_without_personalization2.png" alt="alt" title="Non-personalized search" />
    <figcaption style="text-align:center; font-style:italic;">Non-personalized search</figcaption>
</figure>
 
The first and third ad is bought positions, while the rest is sorted by published time and the importance of the word chair.<br>
 
In the fall of 2017, we started experimenting with ways to improve the relevancy sorting. First try was by boosting geo distance, ads close to my position would get a higher score.
But we could not see any positive changes for our product KPIs. Then we wanted to try sort by mixing scores from both SOLR and our recommendation system.
Our recommendation system already had an api where we could send a user id and a list of ad ids, and receive a recommendation score for each of the ads.

## A new solution

We evaluated a few different solutions, mainly:
- learning to rank in SOLR
- custom SOLR plugin using the existing recommendations api

Since we already had a system for recommendations, and awesome data scientists tuning the algorithms and so on, we chose to test this solution. 
A uncertainty was if we could get the response times needed for a search.

### SOLR SearchComponent
A SOLR search component contains several phases used by the search handler. As we do not use sharded indices for the search we wanted to test, these are the important phases:
- prepare - Preparing the response -> parsing request parameters
- process - Processing the request for the current component

### Requirements
- the personalization score should influence the order of the search result
- we want to easily be able to change recommendation algorithm, and test a new one against the current search (with or without personalization)
- we also wanted to tune SOLR score/recommendation score ballance

We therefore added these parameters:
- personalization (Boolean) - to switch on/off the personalization search
- recommender id (String) - to set recommendation algorithm
- rerank pages (Integer) - number of pages with personalization per search, typically the first 5 or 10 pages will be personalized. This is to increase performance, compared to calculating personalization scores for every document in a search result.
- personalization score weight (Double) - recommendation score weight, 1= equal weight to SOLR score
- user id (String) - to be able to personalize the search

The solution we chose for the plugin is:<br/>
1) Let the search fetch all documents from the first x pages, set by the parameter rerank_pages.<br/>
2) Fetch recommendation score for each document returned by the search from the recommendations api.<br/>
3) Sort the documents by a combination of SOLR score and recommendation score.<br/>
4) Return a subset of the search result, based on offset and rows parameters

We want the search from the default QueryComponent to run before the PersonalizationComponent, since we need the ad ids (SOLR document ids) for the first x pages of the search result. Therefore we setup our component as a last-component in the solrconfig. 

    <requestHandler name="dismax" class="no.finntech.search.solr.searchhandler.SearchHandler" default="true">
        ..
        ..
        <arr name="last-components">
            <str>personalization</str>
        </arr>
    </requestHandler>


### SearchComponent.prepare:

The prepare step are evaluating and setting the search parameters count, offset and score. 

    @Override
    public void prepare(ResponseBuilder rb) throws IOException {
       if (!shouldWePersonalize(rb.req)) {
           return;
       }
    
       final SolrParams params = rb.req.getParams();
       final SortSpec sortSpec = rb.getSortSpec();
       final int offset = sortSpec.getOffset();
       final int reRankNum = getReRankNum(params);
    
       if (reRankNum > offset) { //reranking only the first x (reRankPages) pages
           sortSpec.setCount(reRankNum);
           sortSpec.setOffset(0);
           if (params.get("score") == null) {//adds score for the search
               rb.setFieldFlags(SolrIndexSearcher.GET_SCORES);
           }
       }
    }

First we are checking the parameters if it is a personalization search. This component will not manipulate a search without personalization.
    
    if (!shouldWePersonalize(rb.req)) {
       return;
    }

The personalization search will reorder the first x pages, set by the parameter rerank_pages, therefore count and offset is set to include the first x pages of the search result.

    if (reRankNum > offset) { //reranking only the first x (reRankPages) pages
               sortSpec.setCount(reRankNum);
               sortSpec.setOffset(0);

Setting the flag which makes the QueryComponent calculate scores:

    if (params.get("score") == null) {//adds score for the search
        rb.setFieldFlags(SolrIndexSearcher.GET_SCORES);
    }


### SearchComponent.process:

In the process step, the recommendation scores are fetched, and the final sorting of the search result are set based on SOLR- and recommendations scores.

    @Override
    public void process(ResponseBuilder rb) throws IOException {
       if (!shouldWePersonalize(rb.req)) {
           return;
       }
       final SolrParams params = rb.req.getParams();
       final String recommenderId = params.get(FINN_PERSONALIZATION_RECOMMENDERID);
       final String userId = params.get(FINN_USERID);
       final int rows = params.getInt(ROWS);
       final int offset = getOffset(params);
       final float personalizationScoreWeight = getPersonalizationScoreWeight(params);
       final int reRankNum = rb.getResults().docList.size();
    
       if (reRankNum <= offset) { //only reranking first x (reRankNum) documents of a search
           return;
       }
    
       final DocList initialSearchResult = rb.getResults().docList;
       List<ScoredAd> results = getInitialSearchDocs(rb, reRankNum, initialSearchResult);
    
       final Map<String, ScoredAd> recommendedItems = getRecommendedItems(userId, recommenderId, reRankNum, results);
    
       if (recommendedItems.size() > 0) {
           results.forEach(result -> {
               final float normalizedSolrScore = normalizedSolrScore(result.getScore(), initialSearchResult.maxScore());
               final float personalizationScore = personalizationScore(personalizationScoreWeight, recommendedItems, result.getAdId());
               result.setScore(normalizedSolrScore + personalizationScore);
           });
           results.sort((r1, r2) -> Float.compare(r2.getScore(), r1.getScore()));
       }
    
       final List<ScoredAd> searchResult = results.stream()
               .skip(offset)
               .limit(rows)
               .collect(toList());
       try {
           addToResponse(rb, reRankNum, searchResult, initialSearchResult);
       } catch (IOException e) {
           LOG.error("Could not add recommended items to search result: " + e);
       }
    }

As in the prepare step, we start the process step by escaping non-personalization searches, checking if the search is a personalization search, and if the search is within the pages to personalize: 
   
    if (!shouldWePersonalize(rb.req)) {
       return;
    }
    
    if (reRankNum <= offset) { //only reranking first x (reRankNum) documents of a search
       return;
    }

The QueryComponent runs it’s process step before the PersonalizationComponent's process, and creates a doclist containing the search results, with lucene document ids and scores.

    final DocList initialSearchResult = rb.getResults().docList;
    List<ScoredAd> results = getInitialSearchDocs(rb, reRankNum, initialSearchResult);

Note that Lucene document id is not the same as SOLR document id. 
Next, to get the SOLR document ids, which we will use for recommendation scores, we need to do a index lookup per document. ```searcher.doc(luceneDocumentId, <Set of fields to obtain>)``` is one of the more costly operations in this plugin. 
In FINN.no’s case we have a relatively small index (up to 1,3 million documents / 4GB), so it should be mostly memory lookups.

    private static List<ScoredAd> getInitialSearchDocs(ResponseBuilder rb, int reRankNum, DocList initialSearchResult) throws IOException {
       List<ScoredAd> results = new ArrayList<>();
       int counter = 0;
       final SolrIndexSearcher searcher = rb.req.getSearcher();
       for (DocIterator docIterator = initialSearchResult.iterator(); docIterator.hasNext() && counter < reRankNum; counter++) {
           results.add(createDoc(searcher, docIterator.next(), docIterator.score()));
       }
       return results;
    }
    
    private static ScoredAd createDoc(SolrIndexSearcher searcher, int luceneDocumentId, float score) throws IOException {
       final Document doc = searcher.doc(luceneDocumentId, Sets.newHashSet("id")); //there should be a better way to do the luceneid -> id mapping
       String adid = doc.get("id");
       return new ScoredAd(luceneDocumentId, adid, score);
    }
 
Next we use the recommendation api to get a personalization score for each document. This is a http-request, and is therefore also a high cost part, latency vise, of the plugin. 
Our api takes the following parameters:
- UserId
- RecommenderId
- List of AdIds (SOLR document ids)
- MaxRows

And returns a list of adIds with recommendation score. ScoredAd now contains both the SOLR- and the recommendation-score. These are further used for sorting the search results: ```total score = normalized solr score + (normalized recommendation score * recommendation score weight)```

    final Map<String, ScoredAd> recommendedItems = getRecommendedItems(userId, recommenderId, reRankNum, results);
    
    if (recommendedItems.size() > 0) {
       results.forEach(result -> {
           final float normalizedSolrScore = normalizedSolrScore(result.getScore(), initialSearchResult.maxScore());
           final float personalizationScore = personalizationScore(personalizationScoreWeight, recommendedItems, result.getAdId());
           result.setScore(normalizedSolrScore + personalizationScore);
       });
       results.sort((r1, r2) -> Float.compare(r2.getScore(), r1.getScore()));
    }

Finally we are preparing the search response with a subset of the search results, set by offset and rows.

    final List<ScoredAd> searchResult = results.stream()
           .skip(offset)
           .limit(rows)
           .collect(toList());
    try {
       addToResponse(rb, reRankNum, searchResult, initialSearchResult);
    } catch (IOException e) {
       LOG.error("Could not add recommended items to search result: " + e);
    }
    
    private static void addToResponse(ResponseBuilder rb, int reRankNum, List<ScoredAd> resultsToReturn, DocList docList) throws IOException {
       DocListAndSet docListAndSet = buildDocListAndSet(reRankNum, resultsToReturn, docList);
       rb.setResults(docListAndSet);
    
       BasicResultContext resultContext = new BasicResultContext(rb);
       SolrQueryResponse rsp = rb.rsp;
       rsp.getValues().removeAll("response");
       rsp.addResponse(resultContext);
    }


## Response times
We will also look at the response times:

Response times for 95th percentile. Default search is searches without any custom plugins, distribution search is the actual search we are trying to replace with personalization search. The actual timings in the 95th percentile doubles from 225ms to 450ms. While the basic search takes around 80ms.

<figure>
    <img class="center-block" src="/images/2018-04-09-personalized-search/95th_percentile_latency.png" alt="alt" title="95th percentile latency" />
    <figcaption style="text-align:center; font-style:italic;">95th percentile latency</figcaption>
</figure>


Lets also look at the 99th percentile for response time. We here removed the default search, because of too much noise, because of low traffic. 
The personalization search’s 99th percentile is between 60 to 100 ms slower than the search to be replaced.

<figure>
    <img class="center-block" src="/images/2018-04-09-personalized-search/99th_percentile_latency.png" alt="alt" title="99th percentile latency" />
    <figcaption style="text-align:center; font-style:italic;">99th percentile latency</figcaption>
</figure>

## Search examples

Here is a couple of examples of different user doing the same search at the same time, which will result in a completely different order of the ads:

<figure>
    <img class="center-block" src="/images/2018-04-09-personalized-search/example_with_personalized1.png" alt="alt" title="example 1 personalized search" />
    <figcaption style="text-align:center; font-style:italic;">Personalized search with a user interested in antiques</figcaption>
</figure>

<figure>
    <img class="center-block" src="/images/2018-04-09-personalized-search/example_with_personalized2.png" alt="alt" title="example 2 personalized search" />
    <figcaption style="text-align:center; font-style:italic;">Personalized search with a user interested in furnitures for children</figcaption>
</figure>

We are now doing a personalization search for the 5 first pages of the search results. 
Both of the searches includes the same 250 ads on the first 5 pages, but as you can see, the order is completely changed between the two examples.
Published dates and the query relevance decides which subset of the search result to personalize.

## Product results
After testing the personalization search for around 3-4 months, we experienced around 2% better results for the some of the most important product KPIs for search:
- Shorter time, in term of seconds, from start of search to clicking a search result
- More user sessions with clicking a search result
- Overall more contacting the sellers


## Summary
The personalization search makes our search slightly better for the our users in FINN.no.
But it does indeed costs more in terms of latency. Here we hope to be able to increase the performance a bit. There are several possibilities:
- cache recommendation matrices within the solr servers, to avoid network overhead
- find a better way to get the SOLR document ids
- hardware resource tuning
- learning to rank feature in SOLR
- ...and so on

We will still tune our recommendations algorithms and the plugin to get our results even better. 
As of April 2018, all users of the “torget” search gets the recommendation search.<br>

Code for the plugin:
[Plugin code](/images/2018-04-09-personalized-search/PersonalizationComponent.java)

#### Comments
There are some pain points when developing a SOLR plugin -> the documentation are incomplete, so a lot of debugging the SOLR components was needed to understand what kind of data is populated in a given step by the default SearchComponents. 
I have also been reading a lot of the SOLR source code, looking for how to solve this problem.
