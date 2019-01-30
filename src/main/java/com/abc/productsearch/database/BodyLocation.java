package com.abc.productsearch.database;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Cacheable
@Entity
@Table(name = "BodyLocation", schema = "abcproducts")
public class BodyLocation implements Serializable, Persistable<Integer> {

    private static final long serialVersionUID = 4505302540820191246L;

    @Id
    @Column(name="body_location_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bodyLocationId;
    
    @Column(name="body_location_name")
    private String bodyLocationName;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, targetEntity=Product.class, mappedBy="bodyLocation", cascade=CascadeType.ALL)
    private List<Product> productList;

    public BodyLocation() {
        // empty
    }
    
    public List<Product> getProductList() {
        return productList;
    }

    public Integer getBodyLocationId() {
        return bodyLocationId;
    }

    public void setBodyLocationId(Integer bodyLocationId) {
        this.bodyLocationId = bodyLocationId;
    }

    public String getBodyLocationName() {
        return bodyLocationName;
    }

    public void setBodyLocationName(String bodyLocationName) {
        this.bodyLocationName = bodyLocationName;
    }

    @JsonIgnore
    @Override
    public Integer getId() {
        return getBodyLocationId();
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }

}
