package com.yehor.databasesaggregation.config;

import com.yehor.databasesaggregation.config.database.MultiRoutingDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager")
public class Config {

    private final String PACKAGE_SCAN = "com.yehor.databasesaggregation.model.entity";


    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public List<DatabaseConfig> databaseConfigs() {
        return new ArrayList<>();
    }

    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource(List<DatabaseConfig> databaseConfigs) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        databaseConfigs.forEach(config -> targetDataSources.put(config.getName(), DataSourceBuilder.create()
                .url(config.getUrl())
                .username(config.getUsername())
                .password(config.getPassword())
                .build()));

        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();

        multiRoutingDataSource.setDefaultTargetDataSource(
                targetDataSources.values().stream()
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("Datasource not found."))
        );
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
    }

    @Bean
    public List<String> databaseNames(List<DatabaseConfig> databaseConfigs) {
        return databaseConfigs.stream()
                .map(DatabaseConfig::getName)
                .toList();
    }

    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager(List<DatabaseConfig> databaseConfigs) {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource(databaseConfigs));
        em.setPackagesToScan(PACKAGE_SCAN);
        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager(List<DatabaseConfig> databaseConfigs) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                multiEntityManager(databaseConfigs).getObject());
        return transactionManager;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean dbSessionFactory(List<DatabaseConfig> databaseConfigs) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource(databaseConfigs));
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);

        return sessionFactoryBean;
    }
}
