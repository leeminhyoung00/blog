package com.test.blog.api.client.naver;

import static org.assertj.core.api.Assertions.assertThat;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NaverSearchConfiguration.class)
class NaverSearchConfigurationTest {

    @InjectMocks
    private NaverSearchConfiguration naverSearchConfiguration;

    @BeforeEach
    public void setUp() {
        naverSearchConfiguration = new NaverSearchConfiguration();
    }

    @Test
    public void requestInterceptorTest() {
        RequestInterceptor interceptor = naverSearchConfiguration.requestInterceptor();
        assertThat(interceptor).isNotNull();
        assertThat(interceptor).isInstanceOf(RequestInterceptor.class);

        feign.RequestTemplate requestTemplate = new feign.RequestTemplate();
        interceptor.apply(requestTemplate);
        assertThat(requestTemplate.headers().get("X-Naver-Client-Id")).contains("gVFKr33sZ76gMJi_1fzG");
        assertThat(requestTemplate.headers().get("X-Naver-Client-Secret")).contains("QHiQ6N7CBl");
    }

    @Test
    public void errorDecoderTest() {
        ErrorDecoder errorDecoder = naverSearchConfiguration.NaverErrorDecode();
        assertThat(errorDecoder).isNotNull();
        assertThat(errorDecoder).isInstanceOf(NaverErrorDecode.class);
    }
}