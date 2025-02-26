package io.github.alberes.guestsjpads.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "io.github.alberes.guestsjpads.repositories",
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        transactionManagerRef = "jpaTransactionManager"
)
public class JPAEntityManagerConfiguration {

    @Primary
    @Bean(name = "jpaEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("jpaDataSource") DataSource dataSource
    ){
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("io.github.alberes.guestsjpads.domains")
                .persistenceUnit("jpa")
                .build();
    }

    @Primary
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager(@Qualifier("jpaEntityManagerFactory")
                                                            EntityManagerFactory jpaEntityManagerFactory){
        return new JpaTransactionManager(jpaEntityManagerFactory);
    }

}
