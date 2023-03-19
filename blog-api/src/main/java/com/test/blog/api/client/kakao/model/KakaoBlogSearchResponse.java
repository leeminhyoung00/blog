package com.test.blog.api.client.kakao.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoBlogSearchResponse {

    private KakaoBlogMeta meta;

    private List<KakaoBlogDocument> documents;

}
