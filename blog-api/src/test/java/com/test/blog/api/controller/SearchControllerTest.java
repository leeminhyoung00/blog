package com.test.blog.api.controller;

import com.test.blog.api.dto.PopularKeywordDto;
import com.test.blog.api.dto.SearchResultDto;
import com.test.blog.api.dto.type.SortType;
import com.test.blog.api.service.SearchManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchManagerService searchManagerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void searchBlogs() throws Exception {
        // Given
        String query = "test";
        SearchResultDto searchResultDto = SearchResultDto.builder().build();
        when(searchManagerService.searchBlogs(query, 1, 10, SortType.ACCURACY)).thenReturn(searchResultDto);

        // When & Then
        mockMvc.perform(get("/search/blog")
                .param("query", query)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getPopularKeywords() throws Exception {
        // Given
        PopularKeywordDto popularKeywordDto = PopularKeywordDto.builder()
            .keyword("test")
            .searchCount(10L)
            .build();
        when(searchManagerService.getPopularKeywords()).thenReturn(Collections.singletonList(popularKeywordDto));

        // When & Then
        mockMvc.perform(get("/search/popular")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].keyword").value("test"))
            .andExpect(jsonPath("$[0].search_count").value(10L));
    }
}
