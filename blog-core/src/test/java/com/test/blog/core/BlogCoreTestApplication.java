package com.test.blog.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.test.blog.core.repository")
@EntityScan(basePackages = "com.test.blog.core.model")
public class BlogCoreTestApplication {
}
