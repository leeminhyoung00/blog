package com.test.blog.api.service;

import com.test.blog.api.client.exception.ClientException;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import com.test.blog.core.exception.BlogException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.test.blog.core.exception.ErrorCode.FAILED_TO_LOAD_BLOG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogSearchServiceChainTest {

    @Mock
    private BlogSearchService kakaoBlogSearchService;

    @Mock
    private BlogSearchService naverBlogSearchService;

    private BlogSearchServiceChain blogSearchServiceChain;

    private SearchResultDto kakaoResult;
    private SearchResultDto naverResult;

    @BeforeEach
    public void setUp() {
        kakaoResult = new SearchResultDto();
        naverResult = new SearchResultDto();
        List<BlogSearchService> services = List.of(kakaoBlogSearchService, naverBlogSearchService);
        blogSearchServiceChain = new BlogSearchServiceChain(services);
    }

    @Test
    public void testSearch_success() {
        when(kakaoBlogSearchService.search("query", 1, 10, SortType.ACCURACY))
            .thenReturn(kakaoResult);

        SearchResultDto result = blogSearchServiceChain.search("query", 1, 10, SortType.ACCURACY);

        assertEquals(kakaoResult, result);
        verify(kakaoBlogSearchService).search("query", 1, 10, SortType.ACCURACY);
        verify(naverBlogSearchService, never()).search("query", 1, 10, SortType.ACCURACY);
    }

    @Test
    public void testSearch_kakaoFails_naverSuccess() {
        when(kakaoBlogSearchService.search("query", 1, 10, SortType.ACCURACY))
            .thenThrow(new ClientException(500, "kakao", FAILED_TO_LOAD_BLOG.getErrorCode(), "failed"));

        when(naverBlogSearchService.search("query", 1, 10, SortType.ACCURACY))
            .thenReturn(naverResult);

        SearchResultDto result = blogSearchServiceChain.search("query", 1, 10, SortType.ACCURACY);

        assertEquals(naverResult, result);
        verify(kakaoBlogSearchService).search("query", 1, 10, SortType.ACCURACY);
        verify(naverBlogSearchService).search("query", 1, 10, SortType.ACCURACY);
    }

    @Test
    public void testSearch_allServicesFail() {
        when(kakaoBlogSearchService.search("query", 1, 10, SortType.ACCURACY))
            .thenThrow(new ClientException(500, "kakao", FAILED_TO_LOAD_BLOG.getErrorCode(), "failed"));

        when(naverBlogSearchService.search("query", 1, 10, SortType.ACCURACY))
            .thenThrow(new ClientException(500, "naver", FAILED_TO_LOAD_BLOG.getErrorCode(), "failed"));

        assertThrows(BlogException.class,
            () -> blogSearchServiceChain.search("query", 1, 10, SortType.ACCURACY));

        verify(kakaoBlogSearchService).search("query", 1, 10, SortType.ACCURACY);
        verify(naverBlogSearchService).search("query", 1, 10, SortType.ACCURACY);
    }
}