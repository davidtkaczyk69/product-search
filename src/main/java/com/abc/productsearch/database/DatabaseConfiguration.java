package com.abc.productsearch.database;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EnableAutoConfiguration
@Configuration
public class DatabaseConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Value("${spring.jpa.hibernate.show_sql:false}")
    private Boolean showSql;

    @Value("${spring.jpa.hibernate.format_sql:false}")
    private Boolean formatSql;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource());
        em.setPackagesToScan("com.abc.productsearch.database");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);

        em.setJpaPropertyMap(properties);

        return em;
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource datasource() {
        return new BasicDataSource();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @PostConstruct
    public void logConnectionPoolSettings() {
        if (LOGGER.isInfoEnabled()) {
            BasicDataSource bds = (BasicDataSource) datasource();
            
            LOGGER.info("Database Connection Pool Initial Size: {}", bds.getInitialSize());
            LOGGER.info("Database Connection Pool Max Total Size: {}", bds.getMaxTotal());
            LOGGER.info("Database Connection Pool Prepared Statements: {}", bds.isPoolPreparedStatements());
        }
    }

}
