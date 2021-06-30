package com.example.todolist.postgresql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "postgresEntityManagerFactory",
        basePackages = {"com.example.todolist.postgresql"},
        transactionManagerRef = "postgresTransactionManager")
public class PostgreSQLDBConfig {

    //    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
    @Bean
    @ConfigurationProperties(prefix = "spring.postgres.datasource")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties("spring.postgres.datasource.configuration")
    public HikariDataSource firstDataSource() {
        return postgresDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("postgresDataSource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.use-new-id-generator-mappings", "false");
//        properties.put("hibernate.show-sql", "true");
//        properties.put("hibernate.format_sql", "true");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
//        properties.put("hibernate.ddl-auto", "create-drop");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.example.todolist.postgresql.model")
                .persistenceUnit("Postgresql")
                .build();
    }

    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
