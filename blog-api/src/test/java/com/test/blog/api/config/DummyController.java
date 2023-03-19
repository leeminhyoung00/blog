package com.test.blog.api.config;

import com.test.blog.core.exception.BlogException;
import com.test.blog.core.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
class DummyController {

    @GetMapping("/exception")
    void throwException() {
        throw new BlogException(ErrorCode.FAILED_TO_LOAD_BLOG);
    }

    @GetMapping("/internal-error")
    void throwInternalError() {
        throw new RuntimeException("Internal server error message");
    }
}

