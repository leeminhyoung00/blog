package com.test.blog.api.service;

import com.test.blog.api.client.naver.NaverSearchClient;
import com.test.blog.api.client.naver.model.NaverBlogItem;
import com.test.blog.api.client.naver.model.NaverBlogSearchResponse;
import com.test.blog.api.dto.BlogDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverBlogSearchServiceImpl implements BlogSearchService {

    private final NaverSearchClient naverSearchClient;

    @Override
    public int order() {
        return 1;
    }

    @Override
    public SearchResultDto search(String query, Integer page, Integer size, SortType sort) {
        String sortOption = sort == SortType.ACCURACY ? "sim" : "date";
        Integer start = ((page - 1) * size) + 1;
        NaverBlogSearchResponse response = naverSearchClient.searchBlog(query, size, start, sortOption);
        return mapToSearchResults(response, size);
    }

    private SearchResultDto mapToSearchResults(NaverBlogSearchResponse response, Integer size) {
        return SearchResultDto.builder()
            .totalCount(response.getTotal())
            .pageCount((response.getTotal() + size - 1) / size)
            .blogs(response.getItems().stream().map(this::mapToBlogDto).collect(Collectors.toList()))
            .build();
    }

    private BlogDto mapToBlogDto(NaverBlogItem naverBlogItem) {
        return BlogDto.builder()
            .blogname(naverBlogItem.getBloggername())
            .contents(naverBlogItem.getDescription())
            .title(naverBlogItem.getTitle())
            .url(naverBlogItem.getBloggerlink())
            .build();
    }
}
