package com.alkemy.ong.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

  @Value("${amazonProperties.endpointUrl}")
  private String endpointUrl;
  @Value("${amazonProperties.accessKeyId}")
  private String accessKey;
  @Value("${amazonProperties.secretAccessKey}")
  private String secretKey;
  @Value("${amazonProperties.region}")
  private String region;
  @Value("${amazonProperties.bucketName}")
  private String bucketName;

  public String getBucketName() {
    return this.bucketName;
  }

  @Bean
  public AmazonS3 initialize() {
    AWSCredentials awsCredentials =
        new BasicAWSCredentials(this.accessKey, this.secretKey);
    return AmazonS3ClientBuilder
        .standard()
        .withRegion(this.region)
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }
}
