package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class EventConsumer {

    @Bean
    public Consumer<Message<String>> consumer() {
        return message -> {
            System.out.println("Message: " + message.getPayload());
            System.out.println("Headers: " + message.getHeaders());
        };
    }

    @Bean
    public Consumer<ErrorMessage> errorHandler() {
        return v -> System.out.println("Can't process message: " + v.getOriginalMessage());
    }
}
