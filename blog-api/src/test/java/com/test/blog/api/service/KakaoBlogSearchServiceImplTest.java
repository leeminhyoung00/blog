package com.test.blog.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.test.blog.api.client.kakao.KakaoSearchClient;
import com.test.blog.api.client.kakao.model.KakaoBlogDocument;
import com.test.blog.api.client.kakao.model.KakaoBlogMeta;
import com.test.blog.api.client.kakao.model.KakaoBlogSearchResponse;
import com.test.blog.api.dto.BlogDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import java.time.ZonedDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KakaoBlogSearchServiceImplTest {

    @InjectMocks
    private KakaoBlogSearchServiceImpl kakaoBlogSearchService;

    @Mock
    private KakaoSearchClient kakaoSearchClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void order() {
        assertEquals(0, kakaoBlogSearchService.order());
    }

    @Test
    void search() {
        // Given
        String query = "test";
        Integer page = 1;
        Integer size = 10;
        SortType sort = SortType.ACCURACY;

        KakaoBlogMeta meta = new KakaoBlogMeta(100, 50, true);
        KakaoBlogDocument document = new KakaoBlogDocument("blogname", "contents", ZonedDateTime.now(), "thumbnail", "title", "url");
        KakaoBlogSearchResponse response = new KakaoBlogSearchResponse(meta, Collections.singletonList(document));

        when(kakaoSearchClient.searchBlog(query, page, size, sort.name().toLowerCase())).thenReturn(response);

        // When
        SearchResultDto result = kakaoBlogSearchService.search(query, page, size, sort);

        // Then
        assertEquals(meta.getTotal_count(), result.getTotalCount());
        assertEquals((meta.getPageable_count() + size - 1) / size, result.getPageCount());
        assertEquals(1, result.getBlogs().size());

        BlogDto blogDto = result.getBlogs().get(0);
        assertEquals(document.getBlogname(), blogDto.getBlogname());
        assertEquals(document.getContents(), blogDto.getContents());
        assertEquals(document.getDatetime(), blogDto.getDatetime());
        assertEquals(document.getThumbnail(), blogDto.getThumbnail());
        assertEquals(document.getTitle(), blogDto.getTitle());
        assertEquals(document.getUrl(), blogDto.getUrl());

        verify(kakaoSearchClient, times(1)).searchBlog(query, page, size, sort.name().toLowerCase());
    }
}