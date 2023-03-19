package com.test.blog.api.client.kakao;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KakaoSearchConfiguration.class)
public class KakaoSearchConfigurationTest {

    @InjectMocks
    private KakaoSearchConfiguration kakaoSearchConfiguration;

    @BeforeEach
    public void setUp() {
        kakaoSearchConfiguration = new KakaoSearchConfiguration();
    }

    @Test
    public void requestInterceptorTest() {
        RequestInterceptor interceptor = kakaoSearchConfiguration.requestInterceptor();
        assertThat(interceptor).isNotNull();
        assertThat(interceptor).isInstanceOf(RequestInterceptor.class);

        feign.RequestTemplate requestTemplate = new feign.RequestTemplate();
        interceptor.apply(requestTemplate);
        assertThat(requestTemplate.headers().get("Authorization")).contains("KakaoAK 4476950d78151a2ae2f3a287ef4e6dcf");
    }

    @Test
    public void errorDecoderTest() {
        ErrorDecoder errorDecoder = kakaoSearchConfiguration.KaKaoErrorDecode();
        assertThat(errorDecoder).isNotNull();
        assertThat(errorDecoder).isInstanceOf(KaKaoErrorDecode.class);
    }

}
