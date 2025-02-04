package com.iposhka.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Component
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DataSourceConfig {
    private final Environment environment;

    public DataSourceConfig(Environment environment){
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(environment.getProperty("spring.datasource.driver-class"));
        String url = environment.getProperty("spring.datasource.url");
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(environment.getProperty("spring.datasource.username"));
        String pass = environment.getProperty("spring.datasource.password");
        hikariConfig.setPassword(pass);
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.hikari.maximum-pool-size", "10")));
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource){
        SpringLiquibase liquiBase = new SpringLiquibase();
        liquiBase.setChangeLog(environment.getProperty("spring.liquebase.change-log"));
        liquiBase.setDataSource(dataSource);
        liquiBase.setShouldRun(Boolean.parseBoolean(environment.getProperty("spring.liquebase.enabled", "true")));
        return liquiBase;
    }

    public Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
        properties.put("hibernate.format_sql", environment.getProperty("spring.jpa.format-sql"));
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
        var factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.iposhka.model");
        factoryBean.setHibernateProperties(hibernateProperties());
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }
}