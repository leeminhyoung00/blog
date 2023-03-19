package com.test.blog.core.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonUtilTest {

    @Test
    public void testGetObjectMapper() {
        ObjectMapper objectMapper1 = SingletonUtil.getObjectMapper();
        ObjectMapper objectMapper2 = SingletonUtil.getObjectMapper();
        assertThat(objectMapper1).isSameAs(objectMapper2);
    }

}
