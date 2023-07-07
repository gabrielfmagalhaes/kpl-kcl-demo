package com.example.demo;

import java.util.Collections;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class DemoService {
    
    @Autowired
    private StreamBridge streamBridge;

    @Bean
    public Consumer<Message<String>> consumer() {
        return message -> {
            System.out.println("Message: " + message.getPayload());
        };
    }

    @Scheduled(fixedDelay = 10000L)
    public void supplier() {
        streamBridge.send("producer-out-0",
            MessageBuilder.createMessage("hello", new MessageHeaders(Collections.singletonMap("test", "hi"))));
    }

}
