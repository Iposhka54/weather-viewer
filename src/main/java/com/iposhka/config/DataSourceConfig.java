package com.iposhka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

    @Bean
    @Profile("dev")
    public DataSource devDataSource(){
        return null;
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource(){
        return null;
    }
}