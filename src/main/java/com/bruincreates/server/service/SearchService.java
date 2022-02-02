package com.bruincreates.server.service;

import com.bruincreates.server.dao.po.Product;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    //TODO: functions called by ProductService to sync MySql data with Elasticsearch
    //e.g. ProductService.java
    //          task: insert a new product
    //          ProductMapper.insertSelective(someProduct); //insert to MySql
    //          SearchService.createDocument(someProduct);  //insert to Elasticsearch

    public void createDocument(Product product) {}

    public void updateDocument(Product product) {}

    public void deleteDocument(Product product) {}

    //TODO: functions for your search logic below:

}
