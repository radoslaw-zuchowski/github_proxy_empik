package com.zuchol.githubproxy.domain;

import com.zuchol.githubproxy.application.CalculationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationServiceTest {

  private final CalculationService calculationService = new CalculationService();

  @Test
  void shouldReturnCorrectValueWhenFollowersNumberIsGreaterThanZero() {
    // given
    int publicRepos = 10;
    int followers = 5;
    double expected = 14.399999999999999;

    // when
    double result = calculationService.calculateCalculations(publicRepos, followers);

    // then
    assertEquals(expected, result);
  }

  @Test
  void shouldReturnZeroWhenFollowersNumberIsZero() {
    // given
    int publicRepos = 10;
    int followers = 0;
    double expected = 0.0;

    // when
    double result = calculationService.calculateCalculations(publicRepos, followers);

    // then
    assertEquals(expected, result);
  }

  @Test
  void shouldHandleCaseWhenThereIsOneFollowers() {
    // given
    int publicRepos = 10;
    int followers = 1;
    double expected = 72;

    // when
    double result = calculationService.calculateCalculations(publicRepos, followers);

    // then
    assertEquals(expected, result);
  }

  @Test
  void shouldReturnZeroWhenFollowersNumberIsNull() {
    // given
    int publicRepos = 10;
    Integer followers = null;
    double expected = 0.0;

    // when
    double result = calculationService.calculateCalculations(publicRepos, followers);

    // then
    assertEquals(expected, result);
  }

  @Test
  void shouldHandleCaseWhenPublicReposNumberIsZero() {
    // given
    int publicRepos = 0;
    int followers = 10;
    double expected = 1.2;

    // when
    double result = calculationService.calculateCalculations(publicRepos, followers);

    // then
    assertEquals(expected, result);
  }

  @Test
  void shouldHandleCaseWhenFollowersNumberIsNegative() {
    // given
    int publicRepos = 10;
    int followers = -5;
    double expected = 0.0;

    // when
    double result = calculationService.calculateCalculations(publicRepos, followers);

    // then
    assertEquals(expected, result);
  }
}
