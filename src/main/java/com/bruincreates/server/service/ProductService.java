package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.ProductMapper;
import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.dao.po.ProductExample;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.CreateProductRequest;
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

        return product;
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
