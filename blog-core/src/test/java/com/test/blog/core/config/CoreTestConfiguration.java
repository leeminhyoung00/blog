package com.test.blog.core.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.blog.core.repository.PopularSearchKeywordCustomRepository;
import javax.persistence.EntityManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.test.blog.core.repository")
@EntityScan(basePackages = "com.test.blog.core.model")
public class CoreTestConfiguration {

    @Bean
    public PopularSearchKeywordCustomRepository popularSearchKeywordCustomRepository(EntityManager entityManager) {
        return new PopularSearchKeywordCustomRepository(new JPAQueryFactory(entityManager));
    }

}
