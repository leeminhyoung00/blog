package com.test.blog.api.service;

import com.test.blog.api.client.kakao.KakaoSearchClient;
import com.test.blog.api.client.kakao.model.KakaoBlogDocument;
import com.test.blog.api.client.kakao.model.KakaoBlogSearchResponse;
import com.test.blog.api.dto.BlogDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoBlogSearchServiceImpl implements BlogSearchService{

    private final KakaoSearchClient kakaoSearchClient;

    @Override
    public int order() {
        return 0;
    }

    @Override
    public SearchResultDto search(String query, Integer page, Integer size, SortType sort) {
        KakaoBlogSearchResponse response = kakaoSearchClient.searchBlog(query, page, size, sort.name().toLowerCase());
        return mapToSearchResults(response, size);
    }

    private SearchResultDto mapToSearchResults(KakaoBlogSearchResponse response, Integer size) {
        return SearchResultDto.builder()
            .totalCount(response.getMeta().getTotal_count())
            .pageCount((response.getMeta().getPageable_count() + size - 1) / size)
            .blogs(response.getDocuments().stream().map(this::mapToDocument).collect(Collectors.toList()))
            .build();
    }

    private BlogDto mapToDocument(KakaoBlogDocument kakaoBlogDocument) {
        return BlogDto.builder()
            .blogname(kakaoBlogDocument.getBlogname())
            .contents(kakaoBlogDocument.getContents())
            .datetime(kakaoBlogDocument.getDatetime())
            .thumbnail(kakaoBlogDocument.getThumbnail())
            .title(kakaoBlogDocument.getTitle())
            .url(kakaoBlogDocument.getUrl())
            .build();
    }
}
