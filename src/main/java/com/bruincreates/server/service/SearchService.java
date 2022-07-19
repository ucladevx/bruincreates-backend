package com.bruincreates.server.service;

import com.bruincreates.server.dao.po.Product;
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

        Set<String> keywordsSet = new HashSet<String>();
        for (int i = 0; i < keywordsList.size(); i++) {
            keywordsSet.add(keywordsList.get(i));
        }

        List<String> processedKeywords = new ArrayList<String>();
        Iterator it = keywordsSet.iterator();
        while (it.hasNext()) {
            processedKeywords.add((String) it.next());
        }

        return processedKeywords;
    }

    private void mergeListsRemovingDuplicates (List<Product> list1, List<Product> list2) {
        list1.removeAll(list2);
        list1.addAll(list2);
    }

    //TODO: functions for your search logic below:
    public SearchResponse getSearchResults(List<String> keywords) {
        List<Product> results = new ArrayList<>();

        for (int i = 0; i < keywords.size(); i++) {
            List<Product> productsWithMatchingTitle = searchRepository.findByProductTitleContaining(keywords.get(i));
            mergeListsRemovingDuplicates(results, productsWithMatchingTitle);

            List<Product> productsWithMatchingDescription = searchRepository.findByProductDescriptionContaining(keywords.get(i));
            mergeListsRemovingDuplicates(results, productsWithMatchingDescription);

            List<Product> productsWithMatchingKeywords = searchRepository.findByKeywordsContaining(keywords.get(i));
            mergeListsRemovingDuplicates(results, productsWithMatchingKeywords);
        }

        SearchResponse response = new SearchResponse();
        response.setProducts(results);
        response.setHits(results.size());
        return response;
    }
}
