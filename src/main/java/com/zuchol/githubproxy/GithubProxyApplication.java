package com.zuchol.githubproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
@EnableRetry
public class GithubProxyApplication {

  public static void main(String[] args) {
    SpringApplication.run(GithubProxyApplication.class, args);
  }
}
