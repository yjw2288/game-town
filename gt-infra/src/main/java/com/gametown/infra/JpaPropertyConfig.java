package com.gametown.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JpaPropertyConfig {
    @Bean(name = "jpaProperties")
    @ConfigurationProperties("spring.jpa")
    public Map<String, String> jpaProperties() {
        return new HashMap<>();
    }

}
