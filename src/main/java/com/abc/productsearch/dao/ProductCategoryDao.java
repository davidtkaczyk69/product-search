package com.abc.productsearch.dao;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.productsearch.database.ProductCategory;
import com.abc.productsearch.database.ProductCategoryRepository;

@Component
public class ProductCategoryDao {

    private ProductCategoryRepository productCategoryRepository;
    
    @Autowired
    public ProductCategoryDao(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategory> findAll() {
        return IteratorUtils.toList(productCategoryRepository.findAll().iterator());        
    }

    public ProductCategory findById(int categoryId) {
        Optional<ProductCategory> pc = productCategoryRepository.findById(categoryId);
        
        return pc.isPresent() ? pc.get() : null;
    }
    
}
