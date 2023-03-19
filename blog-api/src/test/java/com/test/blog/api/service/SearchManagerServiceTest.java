package com.test.blog.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.test.blog.api.dto.PopularKeywordDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import com.test.blog.core.dao.PopularKeywordDao;
import com.test.blog.core.model.PopularSearchKeyword;
import com.test.blog.core.service.KeywordPublisher;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchManagerServiceTest {

    private BlogSearchServiceChain blogSearchServiceChain;
    private KeywordPublisher keywordPublisher;
    private PopularKeywordDao popularKeywordDao;
    private SearchManagerService searchManagerService;

    @BeforeEach
    void setUp() {
        blogSearchServiceChain = mock(BlogSearchServiceChain.class);
        keywordPublisher = mock(KeywordPublisher.class);
        popularKeywordDao = mock(PopularKeywordDao.class);
        searchManagerService = new SearchManagerService(blogSearchServiceChain, keywordPublisher, popularKeywordDao);
    }

    @Test
    void searchBlogsTest() {
        SearchResultDto searchResult = new SearchResultDto();
        when(blogSearchServiceChain.search("query", 1, 10, SortType.ACCURACY)).thenReturn(searchResult);

        SearchResultDto result = searchManagerService.searchBlogs("query", 1, 10, SortType.ACCURACY);

        assertEquals(searchResult, result);
        verify(keywordPublisher).publish("query");
    }

    @Test
    void getPopularKeywordsTest() {
        List<PopularSearchKeyword> popularKeywords = new ArrayList<>();
        popularKeywords.add(new PopularSearchKeyword(1L, "keyword1", 100L, 1));
        popularKeywords.add(new PopularSearchKeyword(2L, "keyword2", 50L, 2));

        when(popularKeywordDao.findPopularKeywords()).thenReturn(popularKeywords);

        List<PopularKeywordDto> result = searchManagerService.getPopularKeywords();

        assertEquals(2, result.size());
        assertEquals("keyword1", result.get(0).getKeyword());
        assertEquals(100, result.get(0).getSearchCount());
        assertEquals("keyword2", result.get(1).getKeyword());
        assertEquals(50, result.get(1).getSearchCount());
    }

}
