package com.test.blog.api.client.kakao.model;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoBlogDocument {

    private String blogname;

    private String contents;

    private ZonedDateTime datetime;

    private String thumbnail;

    private String title;

    private String url;
}
