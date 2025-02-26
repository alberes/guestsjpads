package io.github.alberes.guestsjpads.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DRDataSourceConfiguration {

    @ConfigurationProperties("spring.datasource.dr")
    @Bean
    public DataSourceProperties drDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name = "drDataSource")
    public DataSource drDataSource(){
        return drDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
