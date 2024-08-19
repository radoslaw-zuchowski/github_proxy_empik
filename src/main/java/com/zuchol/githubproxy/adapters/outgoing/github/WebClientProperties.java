package com.zuchol.githubproxy.adapters.outgoing.github;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("webclient")
public class WebClientProperties {
  private ClientProperties github;

  public ClientProperties getGithub() {
    return github;
  }

  public void setGithub(ClientProperties github) {
    this.github = github;
  }

  public static final class ClientProperties {
    private String url;
    private Integer readTimeout;
    private Integer connectionTimeout;
    private CacheProperties cache;
    private RetryProperties retry;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public Integer getReadTimeout() {
      return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
      this.readTimeout = readTimeout;
    }

    public Integer getConnectionTimeout() {
      return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
    }

    public CacheProperties getCache() {
      return cache;
    }

    public void setCache(CacheProperties cache) {
      this.cache = cache;
    }

    public RetryProperties getRetry() {
      return retry;
    }

    public void setRetry(RetryProperties retry) {
      this.retry = retry;
    }

    public static final class CacheProperties {
      private Integer expireAfter;
      private Integer maximumSize;

      public Integer getExpireAfter() {
        return expireAfter;
      }

      public void setExpireAfter(Integer expireAfter) {
        this.expireAfter = expireAfter;
      }

      public Integer getMaximumSize() {
        return maximumSize;
      }

      public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
      }
    }

    public static final class RetryProperties {
      private Integer maxAttempts;
      private Integer backoffDuration;

      public Integer getMaxAttempts() {
        return maxAttempts;
      }

      public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
      }

      public Integer getBackoffDuration() {
        return backoffDuration;
      }

      public void setBackoffDuration(Integer backoffDuration) {
        this.backoffDuration = backoffDuration;
      }
    }
  }
}
