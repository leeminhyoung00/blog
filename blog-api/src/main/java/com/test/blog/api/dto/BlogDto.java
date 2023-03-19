package com.test.blog.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
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
public class BlogDto {

    @ApiModelProperty(notes = "블로그 이름")
    private String blogname;

    @ApiModelProperty(notes = "블로그 글 설명")
    private String contents;

    @ApiModelProperty(notes = "블로그 작성시간")
    private ZonedDateTime datetime;

    @ApiModelProperty(notes = "블로그 썸네일 URL")
    private String thumbnail;

    @ApiModelProperty(notes = "블로그 글 제목")
    private String title;

    @ApiModelProperty(notes = "블로그 글 URL")
    private String url;

}
