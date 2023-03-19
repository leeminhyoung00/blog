package com.test.blog.api.client.naver.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NaverBlogSearchResponse {

    private Integer total; // 총 검색 결과 개수

    private Integer start; // 검색 시작 위치

    private Integer display; // 한번에 표시 할 검색 결과 개수

    private List<NaverBlogItem> items;

}
