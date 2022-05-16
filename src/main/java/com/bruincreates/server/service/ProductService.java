package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.ProductMapper;
import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.dao.po.ProductExample;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.CreateProductRequest;
import com.bruincreates.server.model.request.UpdateProductRequest;
import com.bruincreates.server.repository.SearchRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private final SearchRepository searchRepository;

    public ProductService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public Product createProduct(String seller, CreateProductRequest request) {
        //construct product body
        Product product = new Product();
        product.setProductType(request.getType().equals("artwork") ? (byte)1 : (byte) 2);
        product.setProductTitle(request.getTitle());
        product.setKeywords(request.getKeywords());
        product.setProductDescription(request.getDescription());
        product.setProductPrice(BigDecimal.valueOf(request.getPrice()));
        product.setProductStock(request.getStock());
        product.setProductImages(concatListValues(request.getImages()));
        product.setProductCategory(concatListValues(request.getCategories()));
        product.setSellerId(seller);
        product.setProductId(UUID.randomUUID().toString());

        //store to mysql database
        productMapper.insertSelective(product);

        //store to elasticsearch database
        searchRepository.save(product);
        return product;
    }

    public void deleteProduct(String username, String productId) {
        //construct query example
        ProductExample example = new ProductExample();
        example.createCriteria().andProductIdEqualTo(productId).andSellerIdEqualTo(username);

        //delete from mysql
        productMapper.deleteByExample(example);

        //delete from es
        searchRepository.deleteByProductId(productId);
    }

    @Transactional
    protected Product checkInventoryAndBuy(String productId) throws BadRequestException {
        //check if product is available
        Product product = findProductByProductId(productId);
        if (product == null || product.getProductStock() <= 0) {
            throw new BadRequestException("product unavailable");
        }

        //decrement stock only if stock haven't changed since this request
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andProductStockEqualTo(product.getProductStock()).andProductIdEqualTo(productId);
        product.setProductStock(product.getProductStock()-1);

        //decrement stock
        productMapper.updateByExampleSelective(product, productExample);
        return product;
    }

    public Product updateProduct(String productId, UpdateProductRequest request) {
        //find the productID of the one we want to update
        Product product = findProductByProductId(productId);

        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andProductIdEqualTo(productId);

        product.setProductType(request.getType().equals("artwork") ? (byte)1 : (byte) 2);
        product.setProductTitle(request.getTitle());
        product.setKeywords(request.getKeywords());
        product.setProductDescription(request.getDescription());
        product.setProductPrice(BigDecimal.valueOf(request.getPrice()));
        product.setProductStock(request.getStock());
        product.setProductImages(concatListValues(request.getImages()));
        product.setProductCategory(concatListValues(request.getCategories()));
        product.setProductId(request.getProductId());

        //update the product
        productMapper.updateByExampleSelective(product, productExample);

        //store to elasticsearch database
        return product;
    }

    private Product findProductByProductId(String productId) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andProductIdEqualTo(productId);
        List<Product> productList = productMapper.selectByExample(productExample);
        return productList.size() == 0 ? null : productList.get(0);
    }

    private String concatListValues(List<String> target) {
        StringBuilder sb = new StringBuilder();
        for (String o : target) {
            sb.append(o).append(" ");
        }
        return sb.toString();
    }
}
