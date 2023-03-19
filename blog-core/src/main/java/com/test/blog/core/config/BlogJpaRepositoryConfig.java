package com.test.blog.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.test.blog.core.repository")
@EntityScan(basePackages = "com.test.blog.core.model")
public class BlogJpaRepositoryConfig {

}
