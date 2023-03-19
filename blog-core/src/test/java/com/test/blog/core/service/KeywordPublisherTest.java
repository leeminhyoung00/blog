package com.test.blog.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class KeywordPublisherTest {

    @InjectMocks
    private KeywordPublisher keywordPublisher;

    @Mock
    private MessageChannel keywordChannel;

    @Captor
    private ArgumentCaptor<Message<String>> messageCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPublish() {
        String keyword = "testKeyword";

        keywordPublisher.publish(keyword);

        verify(keywordChannel).send(messageCaptor.capture());
        Message<String> sentMessage = messageCaptor.getValue();
        assertEquals(keyword, sentMessage.getPayload());
    }
}
