package io.github.alberes.guestsjpads.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "io.github.alberes.guestsjpads.dao",
        entityManagerFactoryRef = "drEntityManagerFactory",
        transactionManagerRef = "drTransactionManager"
)
public class DREntityManagerConfiguration {

    @Bean(name = "drEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean drEntityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("drDataSource") DataSource dataSource
    ){
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("io.github.alberes.guestsjpads.dr.domains")
                .persistenceUnit("dr")
                .build();
    }

    @Bean(name = "drTransactionManager")
    public PlatformTransactionManager drTransactionManager(@Qualifier("drEntityManagerFactory")
                                                            EntityManagerFactory drEntityManagerFactory){
        return new JpaTransactionManager(drEntityManagerFactory);
    }
}
