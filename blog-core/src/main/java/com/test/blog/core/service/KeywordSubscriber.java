package com.test.blog.core.service;

import com.test.blog.core.dao.PopularKeywordDao;
import com.test.blog.core.model.PopularSearchKeyword;
import java.util.Objects;
import javax.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KeywordSubscriber {

    private final PopularKeywordDao popularKeywordDao;

    private final KeywordPublisher keywordPublisher;

    @ServiceActivator(inputChannel = "keywordChannel")
    @Transactional
    public void handleKeywordChannel(Message<String> message) {
        int maxRetries = 3;
        int currentRetry = 0;

        while(currentRetry < maxRetries) {
            try {
                PopularSearchKeyword keyword = popularKeywordDao.findByKeyword(message.getPayload());
                if(Objects.isNull(keyword)) {
                    keyword = PopularSearchKeyword.builder()
                        .searchCount(0L)
                        .keyword(message.getPayload())
                        .build();
                }
                keyword.setSearchCount(keyword.getSearchCount() + 1);
                popularKeywordDao.save(keyword);
                break;
            } catch (ObjectOptimisticLockingFailureException lockingException) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
                currentRetry++;
            }

        }

        if (currentRetry == maxRetries) {
            keywordPublisher.publish(message.getPayload());
            throw new OptimisticLockException("Failed to update the keyword after " + maxRetries + " retries.");
        }
    }
}
