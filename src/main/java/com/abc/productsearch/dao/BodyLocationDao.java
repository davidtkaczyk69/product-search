package com.abc.productsearch.dao;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.productsearch.database.BodyLocation;
import com.abc.productsearch.database.BodyLocationRepository;

@Component
public class BodyLocationDao {

    private BodyLocationRepository bodyLocationRepository;

    @Autowired
    public BodyLocationDao(BodyLocationRepository bodyLocationRepository) {
        this.bodyLocationRepository = bodyLocationRepository;
    }
    
    public List<BodyLocation> findAll() {
        return IteratorUtils.toList(bodyLocationRepository.findAll().iterator());
    }
    
    public BodyLocation findById(int bodyLocationId) {
        Optional<BodyLocation> bl = bodyLocationRepository.findById(bodyLocationId);
        
        return bl.isPresent() ? bl.get() : null;
    }
    
    public BodyLocation findByBodyLocationName(String bodyLocationName) {
        return bodyLocationRepository.findByBodyLocationName(bodyLocationName);
    }
}
