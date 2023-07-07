package com.example.demo;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.messaging.support.GenericMessage;

import reactor.core.publisher.Flux;

@Configuration
public class DemoService {
    
    @Bean
    public Consumer<Map<String, Object>> consumer() {
        return message -> {
            System.out.println("Message: " + message);
        };
    }

	int messageId = 0;

    @Bean
	public Supplier<Flux<Message<String>>> producer() {
		return () -> Flux.just("test")
            .map(GenericMessage::new);
	}
}
