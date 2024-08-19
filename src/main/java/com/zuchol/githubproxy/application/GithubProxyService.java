package com.zuchol.githubproxy.application;

import com.zuchol.githubproxy.application.dto.User;
import com.zuchol.githubproxy.application.dto.UserWithCalculations;
import org.springframework.stereotype.Service;

@Service
public class GithubProxyService {

  private final GithubClient githubClient;
  private final CalculationService calculationService;
  private final MetricService metricService;

  public GithubProxyService(
      GithubClient githubClient,
      CalculationService calculationService,
      MetricService metricService) {
    this.githubClient = githubClient;
    this.calculationService = calculationService;
    this.metricService = metricService;
  }

  public UserWithCalculations getUserData(String login) {
    metricService.updateCounterForSeller(login);
    var githubUser = githubClient.getGithubUser(login);
    var calculations = calculationService.calculateCalculations(
            githubUser.publicRepos(),
            githubUser.followers()
    );
    return mapToUserWithCalculations(githubUser, calculations);
  }

  private UserWithCalculations mapToUserWithCalculations(User githubUser, Double calculations) {
    return new UserWithCalculations(
        githubUser.id(),
        githubUser.login(),
        githubUser.name(),
        githubUser.type(),
        githubUser.avatarUrl(),
        githubUser.createdAt(),
        calculations
    );
  }
}
