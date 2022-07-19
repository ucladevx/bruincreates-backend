package com.bruincreates.server.service;

import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.model.request.SearchRequest;
import com.bruincreates.server.model.response.SearchResponse;
import com.bruincreates.server.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public List<String> processKeywords(String keywords) {
        List<String> keywordsList = Arrays.asList(keywords.split("\\P{L}+"));
        int keywordsListLength = keywordsList.size();

        Set<String> keywordsSet = new HashSet<String>();
        for (int i = 0; i < keywordsListLength; i++) {
            keywordsSet.add(keywordsList.get(i));
        }

        List<String> processedKeywords = new ArrayList<String>();
        Iterator it = keywordsSet.iterator();
        while (it.hasNext()) {
            processedKeywords.add((String) it.next());
        }

        return processedKeywords;
    }

    //TODO: functions for your search logic below:
    public SearchResponse searchDocument(SearchRequest request) {
        String keywords = request.getKeywords();
        List<Product> productMatch = searchRepository.findByProductTitle(keywords);
        SearchResponse response = new SearchResponse();
        response.setProducts(productMatch);
        response.setHits(productMatch.size());
        return response;
    }
}
