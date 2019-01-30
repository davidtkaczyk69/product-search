package com.abc.productsearch.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSourceRepository extends CrudRepository<ProductSource, Integer> {
    
    ProductSource findBySourceName(String sourceName);
    
}
