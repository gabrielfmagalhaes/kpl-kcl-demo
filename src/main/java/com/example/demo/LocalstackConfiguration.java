package com.example.demo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;

@Configuration
public class LocalstackConfiguration {
    
    private final String endpoint = "http://localhost:4566";

    @Value("${cloud.aws.region.static}")
    private  String region;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
            .region(Region.of(region))
            .endpointOverride(URI.create(endpoint))
            .build();
    }

    @Bean
    public KinesisAsyncClient kinesisAsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
        return KinesisAsyncClient.builder()
            .credentialsProvider(awsCredentialsProvider)
            .region(Region.of(region))
            .endpointOverride(URI.create(endpoint))
            .build();
    }
}
