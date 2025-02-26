package io.github.alberes.guestsjpads.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JPADataSourceConfiguration {

    @ConfigurationProperties("spring.datasource.jpa")
    @Bean
    public DataSourceProperties jpaDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "jpaDataSource")
    public DataSource jpaDataSource(){
        return jpaDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
