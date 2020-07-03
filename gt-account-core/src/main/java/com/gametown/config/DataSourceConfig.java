package com.gametown.config;

import com.gametown.infra.ReplicationRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
public class DataSourceConfig {

    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                        @Qualifier("readDataSource") DataSource readDataSource) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>();
        dataSourceMap.put("write", writeDataSource);
        dataSourceMap.put("read", readDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Bean(name = "lazyDataSource")
    public DataSource lazyDataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "writeDataSource")
    @ConfigurationProperties("spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "readDataSource")
    @ConfigurationProperties("spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }
}
