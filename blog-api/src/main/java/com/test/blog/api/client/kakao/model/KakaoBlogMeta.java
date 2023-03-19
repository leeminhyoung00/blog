package com.test.blog.api.client.kakao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoBlogMeta {

    private Integer total_count;

    private Integer pageable_count;

    private Boolean is_end;

}
