package com.example.demo;

import org.apache.logging.log4j.Logger;

import java.util.Map;

import org.apache.logging.log4j.LogManager;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Tracer.SpanInScope;
import io.micrometer.tracing.Span;

@Component
public class EventProducer {

    static final Logger log = LogManager.getLogger(EventProducer.class.getName());

	@Autowired
	private StreamBridge streamBridge;

    @Autowired
    private Tracer tracer;
    
    @Scheduled(fixedRate = 10000)
    public void producer() {
        Span newSpan = tracer.nextSpan().name("send-message-span").start();

        try (SpanInScope ws = tracer.withSpan(newSpan.start())) {
            MDC.put("traceId", newSpan.context().traceId());
            MDC.put("spanId", newSpan.context().spanId());

            log.info("Publishing message to kinesis stream");

            var produceMessage = MessageBuilder.createMessage("hello", 
                new MessageHeaders(Map.of(
                    "traceId", newSpan.context().traceId(),
                    "spanId", newSpan.context().spanId())));

            streamBridge.send("producer-out-0", "kinesis", produceMessage);
        } finally {
            MDC.remove("traceId");
            MDC.remove("spanId");

            newSpan.end();
        }

    }
}
