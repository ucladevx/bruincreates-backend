package com.bruincreates.server.service;

import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.model.request.SearchRequest;
import com.bruincreates.server.model.response.SearchResponse;
import com.bruincreates.server.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

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
