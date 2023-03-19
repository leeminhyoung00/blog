package com.test.blog.api.client.naver;

import static com.test.blog.core.exception.ErrorCode.*;
import static com.test.blog.core.support.SingletonUtil.getObjectMapper;

import com.test.blog.api.client.exception.ClientException;
import com.test.blog.api.client.naver.model.NaverErrorResponse;
import com.test.blog.core.exception.BlogException;
import com.test.blog.core.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class NaverErrorDecode implements ErrorDecoder {

    private static final String SERVICE_NAME = "naver";

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            NaverErrorResponse errorResponseDto = getObjectMapper().readValue(response.body().asInputStream(), NaverErrorResponse.class);
            return new ClientException(response.status(), SERVICE_NAME, errorResponseDto.getErrorCode(), errorResponseDto.getErrorMessage());
        } catch (Exception e) {
            return new ClientException(500, SERVICE_NAME, FAILED_TO_LOAD_BLOG.getErrorCode(), e.getMessage());
        }
    }
}
