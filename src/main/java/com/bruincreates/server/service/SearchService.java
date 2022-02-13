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
    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    //TODO: functions called by ProductService to sync MySql data with Elasticsearch
    //e.g. ProductService.java
    //          task: insert a new product
    //          ProductMapper.insertSelective(someProduct); //insert to MySql
    //          SearchService.createDocument(someProduct);  //insert to Elasticsearch

    public void createDocument(Product product) {}

    public void updateDocument(Product product) {}

    public void deleteDocument(Product product) {}

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
