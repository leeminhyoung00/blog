package com.test.blog.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeywordPublisher {

    private final MessageChannel keywordChannel;

    public void publish(String keyword) {
        keywordChannel.send(MessageBuilder.withPayload(keyword).build());
    }

}
