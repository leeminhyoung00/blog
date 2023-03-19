package com.test.blog.api.client.naver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.test.blog.api.client.exception.ClientException;
import com.test.blog.api.client.kakao.KaKaoErrorDecode;
import com.test.blog.core.exception.BlogException;
import com.test.blog.core.exception.ErrorCode;
import feign.Request;
import feign.Request.HttpMethod;
import feign.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class NaverErrorDecodeTest {

    private NaverErrorDecode naverErrorDecode;

    @BeforeEach
    public void setUp() {
        naverErrorDecode = new NaverErrorDecode();
    }

    @Test
    public void testDecode_validErrorResponse() throws IOException {
        String methodKey = "testMethodKey";
        int httpStatus = 400;
        String errorType = "INVALID_ARGUMENT";
        String errorMessage = "Invalid argument provided.";

        String errorResponseJson = String.format("{\"errorCode\":\"%s\",\"errorMessage\":\"%s\"}", errorType, errorMessage);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(errorResponseJson.getBytes(StandardCharsets.UTF_8));

        Request request = Request.create(
            HttpMethod.GET,
            "http://localhost:8080",
            Map.of(),
            null,
            StandardCharsets.UTF_8,
            null);

        Response response = Response.builder()
            .request(request)
            .status(httpStatus)
            .body(inputStream.readAllBytes())
            .build();

        ClientException exception = (ClientException) naverErrorDecode.decode(methodKey, response);

        assertEquals(httpStatus, exception.getStatus());
        assertEquals(errorType, exception.getErrorCode());
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testDecode_invalidErrorResponse() {
        String methodKey = "testMethodKey";
        int httpStatus = 500;
        String invalidJson = "{\"invalidJson\":}";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(invalidJson.getBytes(StandardCharsets.UTF_8));

        Request request = Request.create(
            HttpMethod.GET,
            "http://localhost:8080",
            Map.of(),
            null,
            StandardCharsets.UTF_8,
            null);

        Response response = Response.builder()
            .request(request)
            .status(httpStatus)
            .body(inputStream.readAllBytes())
            .build();

        ClientException exception = (ClientException) naverErrorDecode.decode(methodKey, response);

        assertEquals(ErrorCode.FAILED_TO_LOAD_BLOG.getErrorCode(), exception.getErrorCode());
    }
}
