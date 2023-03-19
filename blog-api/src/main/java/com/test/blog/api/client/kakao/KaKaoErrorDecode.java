package com.test.blog.api.client.kakao;

import static com.test.blog.core.exception.ErrorCode.FAILED_TO_LOAD_BLOG;
import static com.test.blog.core.support.SingletonUtil.*;

import com.test.blog.api.client.exception.ClientException;
import com.test.blog.api.client.kakao.model.KakaoErrorResponse;
import com.test.blog.core.exception.BlogException;
import com.test.blog.core.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class KaKaoErrorDecode implements ErrorDecoder {

    private static final String SERVICE_NAME = "kakao";

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            KakaoErrorResponse errorResponseDto = getObjectMapper().readValue(response.body().asInputStream(), KakaoErrorResponse.class);
            return new ClientException(response.status(), SERVICE_NAME, errorResponseDto.getErrorType(), errorResponseDto.getMessage());
        } catch (Exception e) {
            return new ClientException(500, SERVICE_NAME, FAILED_TO_LOAD_BLOG.getErrorCode(), e.getMessage());
        }
    }
}
