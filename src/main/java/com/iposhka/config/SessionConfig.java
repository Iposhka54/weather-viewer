package com.iposhka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SessionConfig {
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;

    @Bean
    @Profile("dev")
    public int devSessionTimeout(){
        return SECONDS_IN_MINUTE * 5;
    }

    @Bean
    @Profile("prod")
    public int prodSessionTimeout(){
        return SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY;
    }
}
