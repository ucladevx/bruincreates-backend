package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.ProductMapper;
import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.dao.po.ProductExample;
import com.bruincreates.server.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

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

}
