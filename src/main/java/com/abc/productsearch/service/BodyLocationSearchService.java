package com.abc.productsearch.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.productsearch.dao.BodyLocationDao;
import com.abc.productsearch.database.BodyLocation;

@RestController()
@RequestMapping("body-location") 
public class BodyLocationSearchService {

    //TODO follow linkability pattern in ProductCategorySearchService
    
    private BodyLocationDao bodyLocationDao;
    
    @Autowired
    public BodyLocationSearchService(BodyLocationDao bodyLocationDao) {
        this.bodyLocationDao = bodyLocationDao;
    }
    
    @GetMapping(produces="application/json" )
    public ResponseEntity<List<BodyLocation>> getBodyLocations() {
        return new ResponseEntity<>(Collections.unmodifiableList(bodyLocationDao.findAll()), HttpStatus.OK);
    }
    
    @GetMapping(value="/{bodyLocationId}", produces="application/json" )
    public ResponseEntity<BodyLocation> getBodyLocation(@PathVariable("bodyLocationId") int bodyLocationId) {
        BodyLocation bl = bodyLocationDao.findById(bodyLocationId);
        
        return new ResponseEntity<>(bl, bl == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    
}
