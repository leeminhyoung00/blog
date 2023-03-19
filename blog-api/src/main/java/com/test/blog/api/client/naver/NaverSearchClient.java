package com.test.blog.api.client.naver;

import com.test.blog.api.client.naver.model.NaverBlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "NaverSearchClient", url = "https://openapi.naver.com", configuration = NaverSearchConfiguration.class)
public interface NaverSearchClient {

    @GetMapping(value = "/v1/search/blog.json")
    NaverBlogSearchResponse searchBlog(@RequestParam("query") String query, @RequestParam("display") Integer display, @RequestParam("start") Integer start, @RequestParam("sort") String sort);

}
