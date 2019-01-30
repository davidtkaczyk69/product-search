package com.abc.productsearch.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.productsearch.dao.ProductCategoryDao;
import com.abc.productsearch.database.ProductCategory;

@RestController()
@RequestMapping("category") 
public class ProductCategorySearchService {

    private ProductCategoryDao productCategoryDao;
    
    @Autowired
    public ProductCategorySearchService(ProductCategoryDao productCategoryDao) {
        this.productCategoryDao = productCategoryDao;
    }
    
    @GetMapping(produces="application/json" )
    public ResponseEntity<List<ProductCategory>> getCategories() {
        List<ProductCategory> pcList = productCategoryDao.findAll();
        pcList.forEach(pc -> {
            Map<String, String> searchLinkMap = new HashMap<>();
            searchLinkMap.put("category", String.valueOf(pc.getCategoryId()));

            pc.add(linkTo(methodOn(ProductCategorySearchService.class).getCategory(pc.getCategoryId())).withSelfRel());
            pc.add(linkTo(methodOn(ProductSearchService.class).getAllProducts(searchLinkMap)).withRel("product.search"));
        });
        
        return new ResponseEntity<>(Collections.unmodifiableList(pcList), HttpStatus.OK);
    }
    
    @GetMapping(value="/{categoryId}", produces="application/json" )
    public ResponseEntity<ProductCategory> getCategory(@PathVariable("categoryId") int categoryId) {
        Map<String, String> searchLinkMap = new HashMap<>();
        searchLinkMap.put("category", String.valueOf(categoryId));
        
        ProductCategory pc = productCategoryDao.findById(categoryId);
        if (pc != null) {
            pc.add(linkTo(methodOn(ProductCategorySearchService.class).getCategory(categoryId)).withSelfRel());
            pc.add(linkTo(methodOn(ProductSearchService.class).getAllProducts(searchLinkMap)).withRel("product.search"));
        }
        
        return new ResponseEntity<>(pc, pc == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    
}
