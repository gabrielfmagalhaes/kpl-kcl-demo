package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Supplier;

@Component
public class EventProducer {

    @Autowired
    private StreamBridge streamBridge;

    @Scheduled(fixedDelay = 15000L)
    public void load() {
        streamBridge.send("test-producer-out-0",
                MessageBuilder.createMessage("hello", new MessageHeaders(Collections.singletonMap("test", "hi"))));
    }
}
