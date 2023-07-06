package com.example.demo;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
public class DemoService {
    
    @Bean
    public Consumer<Message<String>> consumer() {
        return message -> {
            System.out.println("Message: " + message.getPayload());
        };
    }
}
