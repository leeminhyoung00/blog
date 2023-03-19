package com.test.blog.core.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.test.blog.core.config.CoreTestConfiguration;
import com.test.blog.core.model.PopularSearchKeyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = CoreTestConfiguration.class)
public class PopularSearchKeywordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PopularSearchKeywordRepository repository;

    @Test
    void saveAndFindByIdTest() {
        PopularSearchKeyword keyword = new PopularSearchKeyword();
        keyword.setKeyword("test");
        keyword.setSearchCount(1L);
        entityManager.persist(keyword);
        entityManager.flush();

        PopularSearchKeyword result = repository.findById(keyword.getId()).orElse(null);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(keyword.getId());
        assertThat(result.getKeyword()).isEqualTo(keyword.getKeyword());
        assertThat(result.getSearchCount()).isEqualTo(keyword.getSearchCount());
    }
}
