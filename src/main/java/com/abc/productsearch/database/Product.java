package com.abc.productsearch.database;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Product", schema = "abcproducts")
public class Product extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 2898449503607127759L;

    @Id
    @Column(name="product_id", unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    
    @Column(name="product_name")
    private String productName;

    @Column(name="product_price")
    private Double productPrice;
    
    @Column(name="body_location_id")
    private Integer bodyLocationId;
    
    @Column(name="category_id")
    private Integer categoryId;
    
    @Column(name="source_id")
    private Integer sourceId;
    
    @Column(name="product_link")
    private String productLink;

    @Column(name="product_image")
    private String productImage;

    @Column(name="company_id")
    private Integer companyId;
    
    @Column(name="original_id")
    private Integer originalId;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch=FetchType.EAGER, optional=true, targetEntity=ProductCategory.class)
    @JoinColumn(name = "category_id", nullable=true, updatable=false, insertable=false)
    private ProductCategory productCategory;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch=FetchType.EAGER, optional=true, targetEntity=BodyLocation.class)
    @JoinColumn(name = "body_location_id", nullable=true, updatable=false, insertable=false)
    private BodyLocation bodyLocation;
    
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch=FetchType.EAGER, optional=true, targetEntity=ProductSource.class)
    @JoinColumn(name = "source_id", nullable=true, updatable=false, insertable=false)
    private ProductSource productSource;
    
    public Product() {
        // empty
    }

    public ProductSource getProductSource() {
        return productSource;
    }

    public void setProductSource(ProductSource productSource) {
        this.productSource = productSource;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    @JsonIgnore
    public Integer getBodyLocationId() {
        return bodyLocationId;
    }

    public void setBodyLocationId(Integer bodyLocationId) {
        this.bodyLocationId = bodyLocationId;
    }

    @JsonIgnore
    public Integer getCategoryId() {
        return categoryId;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @JsonIgnore
    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    @JsonIgnore
    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    @JsonIgnore
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @JsonIgnore
    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

}
