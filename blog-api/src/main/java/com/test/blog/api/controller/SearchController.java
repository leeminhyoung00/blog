package com.test.blog.api.controller;

import com.test.blog.api.dto.BlogApiErrorResponse;
import com.test.blog.api.dto.PopularKeywordDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import com.test.blog.api.service.SearchManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "검색 API")
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "요청 에러", response = BlogApiErrorResponse.class),
    @ApiResponse(code = 500, message = "서버 에러", response = BlogApiErrorResponse.class)
})
@RestController
@RequestMapping(value = "search")
@RequiredArgsConstructor
@Validated
public class SearchController {

    private final SearchManagerService searchManagerService;

    @ApiOperation(value = "블로그 검색 API")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "query", value = "검색어", required = true),
        @ApiImplicitParam(name = "page", value = "페이지 번호, 1~50 사이의 값, 기본 값 1", example = "1"),
        @ApiImplicitParam(name = "size", value = "한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10", example = "10"),
        @ApiImplicitParam(name = "sort", value = "결과 문서 정렬 방식, ACCURACY(정확도순) 또는 RECENCY(최신순), 기본 값 ACCURACY",  example = "ACCURACY")
    })
    @GetMapping(value = "/blog", produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchResultDto searchBlogs(@RequestParam @NotBlank String query,
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1) @Max(value = 50) Integer page,
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1) @Max(value = 50) Integer size,
        @RequestParam(required = false, defaultValue = "ACCURACY") SortType sort) {
        return searchManagerService.searchBlogs(query, page, size, sort);
    }

    @ApiOperation(value = "인기 검색어 목록 API")
    @GetMapping(value = "/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PopularKeywordDto> getPopularKeywords() {
        return searchManagerService.getPopularKeywords();
    }

}
