package com.test.blog.api.client.naver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NaverErrorResponse {

    private String errorCode;

    private String errorMessage;

}
