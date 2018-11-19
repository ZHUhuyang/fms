package com.fms.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class KafkaMessageListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaMessageListener.class);

    public final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "0", topics = {"test88"})
    public void listen1(String foo) {
        LOGGER.info("消费的结果是==》"+foo);
        this.latch1.countDown();
    }


}
