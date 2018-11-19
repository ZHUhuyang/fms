package com.fms.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class KafkaTest {
    @Autowired
    public KafkaMessageListener listener;

    @Autowired
    public KafkaTemplate<Integer, String> template;

    @Test
    public void testSimple() throws Exception {
        template.send("test0811", 0, "foo");
        template.flush();
        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
    }
}
