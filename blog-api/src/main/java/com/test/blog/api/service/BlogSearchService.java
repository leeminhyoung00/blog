package com.test.blog.api.service;

import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;

public interface BlogSearchService {

    int order();

    SearchResultDto search(String query, Integer page, Integer size, SortType sort);

}
