package com.zuchol.githubproxy.adapters.outgoing.github;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.zuchol.githubproxy.adapters.outgoing.github.WebClientProperties.ClientProperties;
import com.zuchol.githubproxy.adapters.outgoing.github.WebClientProperties.ClientProperties.CacheProperties;
import com.zuchol.githubproxy.adapters.outgoing.github.WebClientProperties.ClientProperties.RetryProperties;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class GithubClientConfig {

  static final String GITHUB_CACHE = "github_cache";
  private final ClientProperties githubClientProperties;

  public GithubClientConfig(WebClientProperties webClientProperties) {
    this.githubClientProperties = webClientProperties.getGithub();
  }

  @Bean("githubClient")
  public RestClient githubClient() {
    ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
            .withReadTimeout(Duration.ofMillis(githubClientProperties.getReadTimeout()))
            .withConnectTimeout(Duration.ofMillis(githubClientProperties.getConnectionTimeout()));
    ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);

    return RestClient.builder()
            .baseUrl(githubClientProperties.getUrl())
            .requestFactory(requestFactory)
            .build();
  }

  @Bean("githubRetryTemplate")
  public RetryTemplate githubRetryTemplate() {
    RetryProperties retryProperties = githubClientProperties.getRetry();

    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(new SimpleRetryPolicy(retryProperties.getMaxAttempts()));
    retryTemplate.setBackOffPolicy(new FixedBackOffPolicy() {
      @Override
      public void setBackOffPeriod(long backOffPeriod) {
        super.setBackOffPeriod(retryProperties.getBackoffDuration());
      }
    });
    return retryTemplate;
  }

  @Bean("githubCache")
  public CaffeineCache githubCache() {
    CacheProperties cacheProperties = githubClientProperties.getCache();
    return new CaffeineCache(
        GITHUB_CACHE,
        Caffeine.newBuilder()
            .expireAfterWrite(cacheProperties.getExpireAfter(), SECONDS)
            .maximumSize(cacheProperties.getMaximumSize())
            .recordStats()
            .build());
  }
}
