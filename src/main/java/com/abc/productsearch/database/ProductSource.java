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
@Table(name = "ProductSource", schema = "abcproducts")
public class ProductSource implements Serializable, Persistable<Integer> {
    
    private static final long serialVersionUID = 5411414190799892564L;

    @Id
    @Column(name="source_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sourceId;
    
    @Column(name="source_name")
    private String sourceName;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, targetEntity=Product.class, mappedBy="productSource", cascade=CascadeType.ALL)
    private List<Product> productList;

    public ProductSource() {
        // empty
    }
    
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @JsonIgnore
    @Override
    public Integer getId() {
        return getSourceId();
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }

}
