package com.test.blog.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResultDto {

    @ApiModelProperty(notes = "검색 결과 수")
    private Integer totalCount;

    @ApiModelProperty(notes = "페이지 수")
    private Integer pageCount;

    @ApiModelProperty(notes = "페이지 검색 결과 목록")
    private List<BlogDto> blogs;

}
