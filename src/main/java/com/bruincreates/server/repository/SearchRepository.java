package com.bruincreates.server.repository;

import com.bruincreates.server.dao.po.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository<Product,Long>{
    List<Product> findByProductTitleContaining(String productName);
    List<Product> findByProductDescriptionContaining(String productName);
    List<Product> findByKeywordsContaining(String productName);

    void deleteByProductId(String productId);
}
