package com.abc.productsearch.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.abc.productsearch.database.Product;
import com.abc.productsearch.database.ProductRepository;

@Component
public class ProductDao {

    private ProductRepository productRepository;
    
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ProductDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(int bodyLocationId) {
        Optional<Product> p = productRepository.findById(bodyLocationId);
        
        return p.isPresent() ? p.get() : null;
    }

    public List<Product> findAll(Map<String, String> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return IteratorUtils.toList(productRepository.findAll().iterator());
        }
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        
        Root<Product> productRoot = cq.from(Product.class);
        productRoot.fetch("productCategory", JoinType.LEFT);
        productRoot.fetch("bodyLocation", JoinType.LEFT);
        productRoot.fetch("productSource", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        
        if (requestParams.containsKey("name")) {
            String name = requestParams.get("name");
            
            // support inbound wildcarding by changing * to % for database like
            name = name.replace('*', '%');
            
            Predicate namePredicate = cb.like(productRoot.get("productName"), "%" + name + "%");
            predicates.add(namePredicate);
        }
        
        if (requestParams.containsKey("category")) {
            Predicate categoryPredicate = cb.equal(productRoot.get("categoryId"), requestParams.get("category"));
            predicates.add(categoryPredicate);
        }
        
        if (requestParams.containsKey("body-location")) {
            Predicate bodyLocationPredicate = cb.equal(productRoot.get("bodyLocationId"), requestParams.get("body-location"));
            predicates.add(bodyLocationPredicate);
        }
        
        if (requestParams.containsKey("source")) {
            Predicate sourcePredicate = cb.equal(productRoot.get("sourceId"), requestParams.get("source"));
            predicates.add(sourcePredicate);
        }
        
        if (!predicates.isEmpty()) {
            cq.where(IteratorUtils.toArray(predicates.iterator(), Predicate.class));
        }

        // future version would probably add sort element to search params and support both asc/desc
        cq.orderBy(cb.desc(productRoot.get("productPrice")));
        
        TypedQuery<Product> query = em.createQuery(cq);
        
        return query.getResultList();
    }

}
