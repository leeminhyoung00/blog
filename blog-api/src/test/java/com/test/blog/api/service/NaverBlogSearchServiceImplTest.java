package com.test.blog.api.service;

import com.test.blog.api.client.naver.NaverSearchClient;
import com.test.blog.api.client.naver.model.NaverBlogItem;
import com.test.blog.api.client.naver.model.NaverBlogSearchResponse;
import com.test.blog.api.dto.BlogDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NaverBlogSearchServiceImplTest {

    @InjectMocks
    private NaverBlogSearchServiceImpl naverBlogSearchService;

    @Mock
    private NaverSearchClient naverSearchClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void order() {
        assertEquals(1, naverBlogSearchService.order());
    }

    @Test
    void search() {
        // Given
        String query = "test";
        Integer page = 1;
        Integer size = 10;
        SortType sort = SortType.ACCURACY;
        String sortOption = sort == SortType.ACCURACY ? "sim" : "date";
        Integer start = page == 1 ? 1 : page * size - 9;

        NaverBlogItem item = new NaverBlogItem("title", "link", "description", "bloggername", "bloggerlink", "postdate");
        NaverBlogSearchResponse response = new NaverBlogSearchResponse(100, 1, 10, Collections.singletonList(item));

        when(naverSearchClient.searchBlog(query, size, start, sortOption)).thenReturn(response);

        // When
        SearchResultDto result = naverBlogSearchService.search(query, page, size, sort);

        // Then
        assertEquals(response.getTotal(), result.getTotalCount());
        assertEquals((response.getTotal() + size - 1) / size, result.getPageCount());
        assertEquals(1, result.getBlogs().size());

        BlogDto blogDto = result.getBlogs().get(0);
        assertEquals(item.getBloggername(), blogDto.getBlogname());
        assertEquals(item.getDescription(), blogDto.getContents());
        assertEquals(item.getTitle(), blogDto.getTitle());
        assertEquals(item.getBloggerlink(), blogDto.getUrl());

        verify(naverSearchClient, times(1)).searchBlog(query, size, start, sortOption);
    }
}