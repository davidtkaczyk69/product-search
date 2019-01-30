package com.abc.productsearch.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {
    
    ProductCategory findByCategoryName(String categoryName);
    
}
