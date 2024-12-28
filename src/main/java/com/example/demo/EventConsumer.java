package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.logging.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Component
public class EventConsumer {

    static final Logger log = LogManager.getLogger(EventConsumer.class.getName());

    @Bean
    public Consumer<Flux<Message<String>>> consumer() {
        return input -> 
            input.map(message -> {
                    MDC.put("traceId", message.getHeaders().get("traceId").toString());
                    MDC.put("spanId", message.getHeaders().get("spanId").toString());
                    
                    log.info("Received message {} along with headers {}", message.getPayload(), message.getHeaders());

                    return message;
                }
            )
            .subscribe();
    }

    @Bean
    public Consumer<ErrorMessage> errorHandler() {
        return v -> System.out.println("Can't process message: " + v.getOriginalMessage());
    }
}
