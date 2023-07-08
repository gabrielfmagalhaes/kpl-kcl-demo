package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Supplier;

@Component
public class EventProducer {

    @Bean
    public Supplier<Message<String>> producer() {
        return () -> MessageBuilder.createMessage("hello", new MessageHeaders(Collections.singletonMap("test", "hi")));
    }
}
