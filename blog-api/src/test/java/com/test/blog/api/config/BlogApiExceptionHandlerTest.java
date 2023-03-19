package com.test.blog.api.config;

import static com.test.blog.core.exception.ErrorCode.FAILED_TO_LOAD_BLOG;
import static com.test.blog.core.exception.ErrorCode.INTERNAL_ERROR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleBlogException() throws Exception {
        mockMvc.perform(get("/test/exception"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.errorCode").value(FAILED_TO_LOAD_BLOG.getErrorCode()))
            .andExpect(jsonPath("$.message").value(FAILED_TO_LOAD_BLOG.getMessage()));
    }

    @Test
    void handleInternalError() throws Exception {
        mockMvc.perform(get("/test/internal-error"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.errorCode").value(INTERNAL_ERROR.getErrorCode()))
            .andExpect(jsonPath("$.message").value(INTERNAL_ERROR.getMessage()));
    }
}
