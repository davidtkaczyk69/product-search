package com.abc.productsearch.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.productsearch.dao.ProductDao;
import com.abc.productsearch.database.Product;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@RestController()
@RequestMapping("product") 
public class ProductSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSearchService.class);
    
    private static final int FIRST_PAGE = 0;

    private static final int DEFAULT_LIMIT = 25;

    private ProductDao productDao;
    
    @Autowired
    public ProductSearchService(ProductDao productDao) {
        this.productDao = productDao;
    }
    
    @GetMapping(value="/{productId}", produces="application/json" )
    public ResponseEntity<Product> getProduct(@PathVariable("productId") int productId) {
        Product p = productDao.findById(productId);
        
        return new ResponseEntity<>(p, p == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    
    @GetMapping(produces="application/json" )
    public ResponseEntity<PaginatedResponse> getAllProducts(@RequestParam Map<String, String> requestParams) {

        // determine page and limits from input data and/or defaults
        
        int limit = DEFAULT_LIMIT;
        if (requestParams.containsKey("limit")) {
            limit = Integer.parseInt(requestParams.get("limit"));
        }
        
        int page = FIRST_PAGE;
        if (requestParams.containsKey("page")) {
            page = Integer.parseInt(requestParams.get("page"));
        }
        
        List<Product> products = productDao.findAll(requestParams);
        int numberOfProducts = products.size();
        
        int endValue = (page * limit) + limit;
        if (endValue > numberOfProducts) {
            endValue = numberOfProducts;
        }
        
        LOGGER.debug("total: {} page: {} end: {}", numberOfProducts, page, endValue);

        // only provide a page worth of data
        products = products.subList(page * limit, endValue);
        
        // add self links to each product
        products.forEach(p -> {
            p.add(linkTo(methodOn(ProductSearchService.class).getProduct(p.getProductId())).withSelfRel());
        });
        
        return new ResponseEntity<>(new PaginatedResponse(numberOfProducts, requestParams, products, page, limit), HttpStatus.OK);
    }

    @JsonPropertyOrder(value = {"page", "limit", "numberOfProducts", "links", "products"})
    private static class PaginatedResponse extends ResourceSupport {
        
        private List<Product> products;
        private Map<String, String> requestParams;
        private int page;
        private int limit;
        private int numberOfProducts;
        
        //TODO this signature could most likely be cleaner
        public PaginatedResponse(int numberOfProducts, Map<String, String> requestParams, List<Product> products, int page, int limit) {
            this.numberOfProducts = numberOfProducts;
            this.products = products;
            this.requestParams = requestParams;
            this.page = page;
            this.limit = limit;
            
            addLinks();
        }

        @SuppressWarnings("unused")
        public int getNumberOfProducts() {
            return numberOfProducts;
        }

        @SuppressWarnings("unused")
        public List<Product> getProducts() {
            return products;
        }

        @SuppressWarnings("unused")
        public int getPage() {
            return page;
        }

        @SuppressWarnings("unused")
        public int getLimit() {
            return limit;
        }

        private void addLinks() {
            int last = (int) Math.ceil(numberOfProducts / limit) - 1;
            if (last < 0) {
                last = 0;
            }
            
            if (page > last) {
                page = last;
                requestParams.put("page", String.valueOf(page));
            }
            
            add(linkTo(methodOn(ProductSearchService.class).getAllProducts(requestParams)).withSelfRel());
            
            requestParams.put("page", "0");
            
            add(linkTo(methodOn(ProductSearchService.class).getAllProducts(requestParams)).withRel("first"));
            
            if (page != last) {
                requestParams.put("page", String.valueOf(page + 1));
                
                add(linkTo(methodOn(ProductSearchService.class).getAllProducts(requestParams)).withRel("next"));
            }
            
            if (page != 0) {
                requestParams.put("page", String.valueOf(page - 1));
                
                add(linkTo(methodOn(ProductSearchService.class).getAllProducts(requestParams)).withRel("previous"));
            }
            
            requestParams.put("page", String.valueOf(last));
            
            add(linkTo(methodOn(ProductSearchService.class).getAllProducts(requestParams)).withRel("last"));
        }
        
    }
    
}
