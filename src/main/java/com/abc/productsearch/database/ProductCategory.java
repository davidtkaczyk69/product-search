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

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Cacheable
@Entity
@Table(name = "ProductCategory", schema = "abcproducts")
public class ProductCategory extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = -6116659114441860127L;

    @Id
    @Column(name="category_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    
    @Column(name="category_name")
    private String categoryName;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, targetEntity=Product.class, mappedBy="productCategory", cascade=CascadeType.ALL)
    private List<Product> productList;
    
    public ProductCategory() {
        // empty
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
