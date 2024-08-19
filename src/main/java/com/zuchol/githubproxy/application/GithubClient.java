package com.zuchol.githubproxy.application;

import com.zuchol.githubproxy.application.dto.User;

public interface GithubClient {
  User getGithubUser(String login);
}
