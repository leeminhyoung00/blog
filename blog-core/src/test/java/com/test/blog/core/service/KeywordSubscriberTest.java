package com.test.blog.core.service;

import com.test.blog.core.dao.PopularKeywordDao;
import com.test.blog.core.model.PopularSearchKeyword;
import javax.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KeywordSubscriberTest {

    @InjectMocks
    private KeywordSubscriber keywordSubscriber;

    @Mock
    private PopularKeywordDao popularKeywordDao;

    @Mock
    private KeywordPublisher keywordPublisher;

    @Captor
    private ArgumentCaptor<PopularSearchKeyword> keywordCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleKeywordChannel_newKeyword() {
        String keywordString = "testKeyword";
        Message<String> message = MessageBuilder.withPayload(keywordString).build();

        when(popularKeywordDao.findByKeyword(keywordString)).thenReturn(null);

        keywordSubscriber.handleKeywordChannel(message);

        verify(popularKeywordDao, times(1)).findByKeyword(keywordString);
        verify(popularKeywordDao, times(1)).save(keywordCaptor.capture());

        PopularSearchKeyword capturedKeyword = keywordCaptor.getValue();
        assertEquals(keywordString, capturedKeyword.getKeyword());
        assertEquals(1, capturedKeyword.getSearchCount());
    }

    @Test
    public void testHandleKeywordChannel_existingKeyword() {
        String keywordString = "testKeyword";
        Message<String> message = MessageBuilder.withPayload(keywordString).build();

        PopularSearchKeyword existingKeyword = PopularSearchKeyword.builder()
            .searchCount(1L)
            .keyword(keywordString)
            .build();

        when(popularKeywordDao.findByKeyword(keywordString)).thenReturn(existingKeyword);

        keywordSubscriber.handleKeywordChannel(message);

        verify(popularKeywordDao, times(1)).findByKeyword(keywordString);
        verify(popularKeywordDao, times(1)).save(keywordCaptor.capture());

        PopularSearchKeyword capturedKeyword = keywordCaptor.getValue();
        assertEquals(keywordString, capturedKeyword.getKeyword());
        assertEquals(2, capturedKeyword.getSearchCount());
    }

    @Test
    public void testHandleKeywordChannel_lockingFailure() {
        String keywordString = "testKeyword";
        Message<String> message = MessageBuilder.withPayload(keywordString).build();

        when(popularKeywordDao.findByKeyword(keywordString))
            .thenThrow(new ObjectOptimisticLockingFailureException(PopularSearchKeyword.class, keywordString));

        try {
            keywordSubscriber.handleKeywordChannel(message);
        } catch (OptimisticLockException e) {
            assertEquals("Failed to update the keyword after 3 retries.", e.getMessage());
            verify(keywordPublisher, times(1)).publish(keywordString);
        }
    }
}
