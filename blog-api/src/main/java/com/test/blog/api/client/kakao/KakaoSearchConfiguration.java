package com.test.blog.api.client.kakao;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class KakaoSearchConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "KakaoAK 4476950d78151a2ae2f3a287ef4e6dcf");
        };
    }

    @Bean
    public ErrorDecoder KaKaoErrorDecode() {
        return new KaKaoErrorDecode();
    }

}
