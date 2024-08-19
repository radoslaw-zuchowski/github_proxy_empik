package com.zuchol.githubproxy.application;

import com.zuchol.githubproxy.adapters.outgoing.database.CounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

  private final CounterRepository repository;

  public MetricService(CounterRepository repository) {
    this.repository = repository;
  }

  private final Logger logger = LoggerFactory.getLogger(MetricService.class);

  @Retryable(
          maxAttempts = 3,
          backoff = @Backoff(delay = 1000)
  )
  public void updateCounterForSeller(String login) {
    logger.info("Updated counter for seller {}", login);
    try {
      repository.getAndUpdateLoginCount(login);
    } catch (Exception e) {
      logger.info("Error during counter update for login {}", login);
      throw e;
    }
  }
}
