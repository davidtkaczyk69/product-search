package com.abc.productsearch.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyLocationRepository extends CrudRepository<BodyLocation, Integer> {
    
    BodyLocation findByBodyLocationName(String bodyLocationName);
    
}
