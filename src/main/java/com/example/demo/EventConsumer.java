package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.binding.BindingsLifecycleController;
import org.springframework.cloud.stream.endpoint.BindingsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class EventConsumer {

    @Autowired
    BindingsEndpoint endpoint;

    @Bean
    public Consumer<Message<String>> consumer() {
        return message -> {
            System.out.println("Message: " + message.getPayload());
            System.out.println("Headers: " + message.getHeaders());
        };
    }

//    @Bean
//    public ApplicationRunner runner() {
//        return args -> {
//            endpoint.changeState("consumer-in-0", BindingsLifecycleController.State.STARTED);
//        };
//    }
}
