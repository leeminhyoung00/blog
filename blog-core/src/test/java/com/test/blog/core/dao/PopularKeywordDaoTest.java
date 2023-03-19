package com.test.blog.core.dao;

import com.test.blog.core.model.PopularSearchKeyword;
import com.test.blog.core.repository.PopularSearchKeywordCustomRepository;
import com.test.blog.core.repository.PopularSearchKeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PopularKeywordDaoTest {

    @Mock
    private PopularSearchKeywordRepository repository;

    @Mock
    private PopularSearchKeywordCustomRepository customRepository;

    @InjectMocks
    private PopularKeywordDao dao;

    private PopularSearchKeyword keyword1;
    private PopularSearchKeyword keyword2;

    @BeforeEach
    void setUp() {
        keyword1 = new PopularSearchKeyword(1L, "keyword1", 50L, 0);
        keyword2 = new PopularSearchKeyword(2L, "keyword2", 100L, 1);
    }

    @Test
    void save() {
        when(repository.save(keyword1)).thenReturn(keyword1);

        PopularSearchKeyword savedKeyword = dao.save(keyword1);

        assertThat(savedKeyword).isNotNull();
        assertThat(savedKeyword.getKeyword()).isEqualTo("keyword1");
        assertThat(savedKeyword.getSearchCount()).isEqualTo(50);

        verify(repository, times(1)).save(keyword1);
    }

    @Test
    void findByKeyword() {
        when(customRepository.findByKeyword("keyword1")).thenReturn(keyword1);

        PopularSearchKeyword foundKeyword = dao.findByKeyword("keyword1");

        assertThat(foundKeyword).isNotNull();
        assertThat(foundKeyword.getKeyword()).isEqualTo("keyword1");
        assertThat(foundKeyword.getSearchCount()).isEqualTo(50);

        verify(customRepository, times(1)).findByKeyword("keyword1");
    }

    @Test
    void findPopularKeywords() {
        List<PopularSearchKeyword> keywords = Arrays.asList(keyword1, keyword2);
        when(customRepository.findPopularKeywords()).thenReturn(keywords);

        List<PopularSearchKeyword> popularKeywords = dao.findPopularKeywords();

        assertThat(popularKeywords).hasSize(2);
        assertThat(popularKeywords.get(0).getKeyword()).isEqualTo("keyword1");
        assertThat(popularKeywords.get(0).getSearchCount()).isEqualTo(50);
        assertThat(popularKeywords.get(1).getKeyword()).isEqualTo("keyword2");
        assertThat(popularKeywords.get(1).getSearchCount()).isEqualTo(100);

        verify(customRepository, times(1)).findPopularKeywords();
    }
}
