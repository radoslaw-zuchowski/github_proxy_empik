package com.zuchol.githubproxy.application;

import org.springframework.stereotype.Service;

@Service
public class CalculationService {

  public double calculateCalculations(Integer publicRepos, Integer followers) {
    if (followers != null && followers > 0) {
      return (double) 6 / followers * (2 + publicRepos);
    }
    return 0;
  }
}
