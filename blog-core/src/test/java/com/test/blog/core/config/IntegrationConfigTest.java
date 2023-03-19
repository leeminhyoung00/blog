package com.test.blog.core.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.support.PeriodicTrigger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = IntegrationConfig.class)
public class IntegrationConfigTest {

    @Autowired
    private QueueChannel keywordChannel;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    @Qualifier(PollerMetadata.DEFAULT_POLLER)
    private PollerMetadata defaultPoller;

    @Test
    void keywordChannelBean() {
        assertThat(keywordChannel).isNotNull();
    }

    @Test
    void taskExecutorBean() {
        assertThat(taskExecutor).isNotNull();
        assertThat(taskExecutor.getCorePoolSize()).isEqualTo(5);
        assertThat(taskExecutor.getMaxPoolSize()).isEqualTo(10);
    }

    @Test
    void defaultPollerBean() {
        assertThat(defaultPoller).isNotNull();
        assertThat(defaultPoller.getTrigger()).isInstanceOf(PeriodicTrigger.class);
        assertThat(defaultPoller.getTaskExecutor()).isSameAs(taskExecutor);
    }
}
