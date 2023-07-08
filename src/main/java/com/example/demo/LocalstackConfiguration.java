package com.example.demo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;

@Configuration
public class LocalstackConfiguration {
    
    private final String endpoint = "http://localhost:4566";

    @Value("${spring.cloud.aws.region.static}")
    private  String region;

    @Value("${spring.cloud.aws.credentials.access-key}")
    private  String accessKey;
    
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private  String secretKey;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    accessKey, secretKey
                )
            ))
            .region(Region.of(region))
            .endpointOverride(URI.create(endpoint))
            .build();
    }

    @Bean
    public KinesisAsyncClient kinesisAsyncClient() {
        return KinesisAsyncClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                    accessKey, secretKey
            )))
            .region(Region.of(region))
            .endpointOverride(URI.create(endpoint))
            .build();
    }

    @Bean
    public CloudWatchAsyncClient cloudWatchAsyncClient() {
        return CloudWatchAsyncClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                    accessKey, secretKey
            )))
            .region(Region.of(region))
            .endpointOverride(URI.create(endpoint))
            .build();
    }

    @Bean
    public KinesisProducerConfiguration kinesisProducerConfiguration() {
        var kinesisProducerConfiguration = new KinesisProducerConfiguration();

        kinesisProducerConfiguration.setCredentialsProvider(new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(accessKey, secretKey)
        ));
        kinesisProducerConfiguration.setRegion(region);
        kinesisProducerConfiguration.setKinesisEndpoint("localhost");
        kinesisProducerConfiguration.setKinesisPort(4566);
        kinesisProducerConfiguration.setCloudwatchEndpoint("localhost");
        kinesisProducerConfiguration.setCloudwatchPort(4566);
        kinesisProducerConfiguration.setStsEndpoint("localhost");
        kinesisProducerConfiguration.setStsPort(4566);
        kinesisProducerConfiguration.setVerifyCertificate(false);

        return kinesisProducerConfiguration;
    }
}
