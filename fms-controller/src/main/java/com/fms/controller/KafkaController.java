package com.fms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fms.service.KafkaTest;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;

/**
 * @author: yer
 * @date: 2018/11/17
 * @description kafka测试
 */

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    @Autowired
    KafkaTest kafkaTest;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void producer() throws Exception {
        kafkaTemplate.send("test88","1234");
    }

    @Test
    public void test1() throws Exception {
        kafkaTemplate.send("test0811","1234");

    }
}