package com.gametown.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "storeEntityManagerFactory",
        transactionManagerRef = "storeTransactionManager",
        basePackages = "com.gametown.store"
)
public class StoreRepositoryConfig {
    @Autowired
    @Qualifier("storeLazyDataSource")
    private DataSource accountLazyDataSource;

    @Bean(name = "storeEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder storeEntityManagerFactoryBuilder(@Qualifier("jpaProperties") Map<String, String> jpaProperties) {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties, null);
    }

    @Primary
    @Bean(name = "storeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("storeEntityManagerFactoryBuilder") EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return entityManagerFactoryBuilder
                .dataSource(accountLazyDataSource)
                .packages("com.gametown.store")
                .build();
    }

    @Primary
    @Bean("storeTransactionManager")
    public PlatformTransactionManager storeTransactionManager(@Qualifier("storeEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
    }
}
