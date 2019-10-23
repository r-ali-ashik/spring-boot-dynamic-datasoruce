package com.aliashik.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
public class DataSourceConfig {

    // Returns Routing DataSource (MyRoutingDataSource)

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    @Autowired
    public DataSource dataSource(DataSource dataSource1, DataSource dataSource2) {

        log.info("## Create DataSource from dataSource1 & dataSource2");

        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setDefaultTargetDataSource(dataSource2);
        dataSource.initDataSources(dataSource1, dataSource2);

        return dataSource;
    }

    @Bean(name = "dataSource1")
    public DataSource dataSource1() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.1"));
        dataSource.setUrl(env.getProperty("spring.datasource.url.1"));
        dataSource.setUsername(env.getProperty("spring.datasource.username.1"));
        dataSource.setPassword(env.getProperty("spring.datasource.password.1"));

        log.info("## DataSource1: " + dataSource);
        return dataSource;
    }

    @Bean(name = "dataSource2")
    public DataSource dataSource2() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.2"));
        dataSource.setUrl(env.getProperty("spring.datasource.url.2"));
        dataSource.setUsername(env.getProperty("spring.datasource.username.2"));
        dataSource.setPassword(env.getProperty("spring.datasource.password.2"));

        log.info("## DataSource2: " + dataSource);

        return dataSource;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();

        txManager.setDataSource(dataSource);

        return txManager;
    }

    @Autowired
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource, JpaProperties jpaProperties) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("com.aliashik.entity");
        entityManagerFactoryBean.setJpaProperties(additionalProperties());
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        return entityManagerFactoryBean;
    }

    Properties additionalProperties() {
        return new Properties() {
            {  // Hibernate Specific:
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("database.hibernate.schema_update"));
                setProperty("hibernate.dialect", env.getProperty("database.hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("database.hibernate.show_sql"));
                setProperty("hibernate.format_sql", env.getProperty("database.hibernate.format_sql"));
            }
        };
    }
}
