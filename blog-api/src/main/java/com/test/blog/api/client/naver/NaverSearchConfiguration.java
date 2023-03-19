package com.test.blog.api.client.naver;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class NaverSearchConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", "gVFKr33sZ76gMJi_1fzG");
            requestTemplate.header("X-Naver-Client-Secret", "QHiQ6N7CBl");
        };
    }

    @Bean
    public ErrorDecoder NaverErrorDecode() {
        return new NaverErrorDecode();
    }

}
