package com.test.blog.api.client.kakao;

import com.test.blog.api.client.kakao.model.KakaoBlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KakaoSearchClient", url = "https://dapi.kakao.com", configuration = KakaoSearchConfiguration.class)
public interface KakaoSearchClient {

    @GetMapping(value = "/v2/search/blog")
    KakaoBlogSearchResponse searchBlog(@RequestParam("query") String query, @RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("sort") String sort);

}
