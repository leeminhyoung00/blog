package com.test.blog.core.repository;

import com.test.blog.core.config.CoreTestConfiguration;
import com.test.blog.core.model.PopularSearchKeyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = CoreTestConfiguration.class)
public class PopularSearchKeywordCustomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PopularSearchKeywordCustomRepository customRepository;

    @Test
    void findByKeywordTest() {
        PopularSearchKeyword keyword = new PopularSearchKeyword();
        keyword.setKeyword("test");
        keyword.setSearchCount(1L);
        entityManager.persist(keyword);
        entityManager.flush();

        PopularSearchKeyword result = customRepository.findByKeyword(keyword.getKeyword());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(keyword.getId());
        assertThat(result.getKeyword()).isEqualTo(keyword.getKeyword());
        assertThat(result.getSearchCount()).isEqualTo(keyword.getSearchCount());
    }

    @Test
    void findPopularKeywordsTest() {
        PopularSearchKeyword keyword1 = new PopularSearchKeyword();
        keyword1.setKeyword("test1");
        keyword1.setSearchCount(2L);
        entityManager.persist(keyword1);

        PopularSearchKeyword keyword2 = new PopularSearchKeyword();
        keyword2.setKeyword("test2");
        keyword2.setSearchCount(1L);
        entityManager.persist(keyword2);

        entityManager.flush();

        List<PopularSearchKeyword> results = customRepository.findPopularKeywords();

        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(keyword1.getId());
        assertThat(results.get(0).getKeyword()).isEqualTo(keyword1.getKeyword());
        assertThat(results.get(0).getSearchCount()).isEqualTo(keyword1.getSearchCount());
        assertThat(results.get(1).getId()).isEqualTo(keyword2.getId());
        assertThat(results.get(1).getKeyword()).isEqualTo(keyword2.getKeyword());
        assertThat(results.get(1).getSearchCount()).isEqualTo(keyword2.getSearchCount());
    }
}
