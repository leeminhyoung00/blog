package com.test.blog.api.client.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.blog.api.client.exception.ClientException;
import com.test.blog.core.exception.BlogException;
import com.test.blog.core.exception.ErrorCode;
import feign.Request;
import feign.Request.HttpMethod;
import feign.Response;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KaKaoErrorDecodeTest {

    private KaKaoErrorDecode kaKaoErrorDecode;

    @BeforeEach
    public void setUp() {
        kaKaoErrorDecode = new KaKaoErrorDecode();
    }

    @Test
    public void testDecode_validErrorResponse() throws IOException {
        String methodKey = "testMethodKey";
        int httpStatus = 400;
        String errorType = "INVALID_ARGUMENT";
        String errorMessage = "Invalid argument provided.";

        String errorResponseJson = String.format("{\"errorType\":\"%s\",\"message\":\"%s\"}", errorType, errorMessage);
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

        ClientException exception = (ClientException) kaKaoErrorDecode.decode(methodKey, response);

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

        ClientException exception = (ClientException) kaKaoErrorDecode.decode(methodKey, response);

        assertEquals(ErrorCode.FAILED_TO_LOAD_BLOG.getErrorCode(), exception.getErrorCode());
    }
}
