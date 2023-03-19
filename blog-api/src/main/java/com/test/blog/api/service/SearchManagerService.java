package com.test.blog.api.service;

import com.test.blog.api.dto.PopularKeywordDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import com.test.blog.core.dao.PopularKeywordDao;
import com.test.blog.core.service.KeywordPublisher;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchManagerService {

    private final BlogSearchServiceChain blogSearchServiceChain;

    private final KeywordPublisher keywordPublisher;

    private final PopularKeywordDao popularKeywordDao;

    public SearchResultDto searchBlogs(String query, Integer page, Integer size, SortType sort) {
        keywordPublisher.publish(query);
        return blogSearchServiceChain.search(query, page, size, sort);
    }

    public List<PopularKeywordDto> getPopularKeywords() {
        return popularKeywordDao.findPopularKeywords().stream()
            .map(k -> PopularKeywordDto.builder()
                .keyword(k.getKeyword())
                .searchCount(k.getSearchCount())
                .build())
            .collect(Collectors.toList());
    }

}
