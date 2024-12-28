package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;

@Configuration
public class OpenTelemetryMicrometerAutoConfiguration {

    @Bean
    public OtlpGrpcSpanExporter otlpGrpcSpanExporter() {
        return OtlpGrpcSpanExporter.builder().setEndpoint("http://localhost:4317/v1/metrics").build();
    }
}
