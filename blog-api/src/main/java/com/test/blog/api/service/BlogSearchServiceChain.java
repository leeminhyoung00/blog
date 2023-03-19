package com.test.blog.api.service;

import com.test.blog.api.client.exception.ClientException;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import com.test.blog.core.exception.BlogException;
import com.test.blog.core.exception.ErrorCode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogSearchServiceChain  {

    private final List<BlogSearchService> services;

    @Autowired
    public BlogSearchServiceChain(List<BlogSearchService> services) {
        this.services = services.stream()
            .sorted(Comparator.comparingInt(BlogSearchService::order))
            .collect(Collectors.toList());
    }

    public SearchResultDto search(String query, Integer page, Integer size, SortType sort) {
        for (BlogSearchService service : services) {
            try {
                return service.search(query, page, size, sort);
            } catch (ClientException e) {
                log.error(e.getErrorMessage());
            }
        }
        throw new BlogException(ErrorCode.FAILED_TO_LOAD_BLOG);
    }

}
