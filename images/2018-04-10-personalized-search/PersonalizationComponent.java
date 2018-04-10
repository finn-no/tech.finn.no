package no.finntech.search.solr.personalization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import no.finntech.search.solr.finn.QueryUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import org.apache.lucene.document.Document;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;
import org.apache.solr.response.BasicResultContext;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.search.DocIterator;
import org.apache.solr.search.DocList;
import org.apache.solr.search.DocListAndSet;
import org.apache.solr.search.DocSetCollector;
import org.apache.solr.search.DocSlice;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.search.SortSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static no.finntech.search.solr.finn.QueryUtil.FINN_PERSONALIZATION_RECOMMENDERID;
import static no.finntech.search.solr.finn.QueryUtil.FINN_PERSONALIZATION_RERANK_PAGES;
import static no.finntech.search.solr.finn.QueryUtil.FINN_USERID;
import static no.finntech.search.solr.finn.QueryUtil.shouldWePersonalize;
import static org.apache.solr.common.params.CommonParams.ROWS;

public class PersonalizationComponent extends SearchComponent {
    private static final Logger LOG = LoggerFactory.getLogger(PersonalizationComponent.class);

    private final RecommendationAPIClient recommendationAPIClient = new RecommendationAPIClient();

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
            if (params.get("score") == null) {//adds score for pre query
                rb.setFieldFlags(SolrIndexSearcher.GET_SCORES);
            }
        }
    }

    private static int getReRankNum(SolrParams params) {
        final int rows = params.getInt(ROWS);
        final Integer reRankPages = params.getInt(FINN_PERSONALIZATION_RERANK_PAGES);
        return rows * (reRankPages == null ? 1 : reRankPages);
    }

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

    private static float normalizedSolrScore(float score, float maxScore) {
        return score / maxScore;
    }

    private static float personalizationScore(float personalizationScoreWeight, Map<String, ScoredAd> recommendedItems, String adId) {
        final float personalizationScore = ofNullable(recommendedItems.get(adId))
                .map(ScoredAd::getScore)
                .filter(s -> s > 0)
                .orElse(0f);
        return personalizationScore * personalizationScoreWeight;
    }

    private static int getOffset(SolrParams params) {
        final String offsetParam = params.get(CommonParams.START);
        return offsetParam == null ? 0 : Integer.parseInt(offsetParam);
    }

    private static float getPersonalizationScoreWeight(SolrParams params) {
        final String scoreWeight = params.get(QueryUtil.FINN_PERSONALIZATION_SCORE_WEIGHT);
        return scoreWeight == null ? 1f : Float.parseFloat(scoreWeight);
    }

    private Map<String, ScoredAd> getRecommendedItems(String userId, String recommenderId, int reRankNum, List<ScoredAd> results) {
         final List<ScoredAd> recommendedItems = recommendationAPIClient
                .recommendItems(recommenderId, userId, reRankNum, adIdsForRecommendations(results));

        return recommendedItems.stream().collect(toMap(ScoredAd::getAdId, Function.identity()));
    }

    List<String> adIdsForRecommendations(List<ScoredAd> results) {
        return results.stream()
                .map(s -> String.valueOf(s.getAdId()))
                .collect(toList());
    }

    private static List<ScoredAd> getInitialSearchDocs(ResponseBuilder rb, int reRankNum, DocList initialSearchResult) throws IOException {
        List<ScoredAd> results = new ArrayList<>();
        int counter = 0;
        final SolrIndexSearcher searcher = rb.req.getSearcher();
        for (DocIterator docIterator = initialSearchResult.iterator(); docIterator.hasNext() && counter < reRankNum; counter++) {
            results.add(createDoc(searcher, docIterator.next(), docIterator.score()));
        }
        return results;
    }

    private static ScoredAd createDoc(SolrIndexSearcher searcher, int internalDocumentId, float score) throws IOException {
        final Document doc = searcher.doc(internalDocumentId, Sets.newHashSet("id")); //there should be a better way to do the luceneid -> id mapping
        String adid = doc.get("id");
        return new ScoredAd(internalDocumentId, adid, score);
    }

    private static void addToResponse(ResponseBuilder rb, int reRankNum, List<ScoredAd> resultsToReturn, DocList docList) throws IOException {
        DocListAndSet docListAndSet = buildDocListAndSet(reRankNum, resultsToReturn, docList);
        rb.setResults(docListAndSet);

        BasicResultContext resultContext = new BasicResultContext(rb);
        SolrQueryResponse rsp = rb.rsp;
        rsp.getValues().removeAll("response");
        rsp.addResponse(resultContext);
    }

    private static DocListAndSet buildDocListAndSet(int reRankNum, List<ScoredAd> resultsToReturn, DocList docList) throws IOException {
        int[] docIds = new int[resultsToReturn.size()];
        float[] scores = new float[resultsToReturn.size()];
        final DocSetCollector docSetCollector = new DocSetCollector(reRankNum, resultsToReturn.size());

        for (int i = 0; i < resultsToReturn.size(); i++) {
            final ScoredAd doc = resultsToReturn.get(i);
            docIds[i] = doc.getLuceneDocId();
            scores[i] = doc.getScore();
            docSetCollector.collect(doc.getLuceneDocId());
        }

        final DocList resortedDocList = new DocSlice(
                0,
                resultsToReturn.size(),
                docIds,
                scores,
                docList.matches(),
                scores[0]
        );

        DocListAndSet results = new DocListAndSet();
        results.docList = resortedDocList;
        results.docSet = docSetCollector.getDocSet();
        return results;
    }

    @Override
    public String getDescription() {
        return "Personalizes the search for userid based on recommendations";
    }
}

