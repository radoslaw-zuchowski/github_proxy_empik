package com.zuchol.githubproxy.adapters.outgoing.github;

import com.zuchol.githubproxy.application.GithubClient;
import com.zuchol.githubproxy.application.dto.GithubClientException;
import com.zuchol.githubproxy.application.dto.LoginNotFoundException;
import com.zuchol.githubproxy.application.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.zuchol.githubproxy.adapters.outgoing.github.GithubClientConfig.GITHUB_CACHE;

@Component
public class GithubClientImpl implements GithubClient {

    private final Logger logger = LoggerFactory.getLogger(GithubClientImpl.class);

    private final RestClient githubClient;
    private final RetryTemplate githubRetryTemplate;

    public GithubClientImpl(RestClient githubClient, RetryTemplate githubRetryTemplate) {
        this.githubClient = githubClient;
        this.githubRetryTemplate = githubRetryTemplate;
    }

    @Override
    @Cacheable(GITHUB_CACHE)
    public User getGithubUser(String login) {
        logger.info("Request to github for user {}", login);
        var githubUser = githubRetryTemplate.execute(context ->
            githubClient.get()
                .uri("users/{login}", login)
                .retrieve()
                .onStatus(status -> status.value() != 200, (request, response) -> {
                    if (response.getStatusCode().value() == 404) {
                        throw new LoginNotFoundException("User " + login + " not found");
                    } else {
                        throw new GithubClientException("An error occurred: " + response.getBody());
                    }
                })
                .body(GithubUserDto.class));
        return toUser(githubUser);
    }

    private User toUser(GithubUserDto githubUser) {
        return new User(
            githubUser.id(),
            githubUser.login(),
            githubUser.name(),
            githubUser.type(),
            githubUser.avatar_url(),
            githubUser.created_at(),
            githubUser.followers(),
            githubUser.public_repos()
        );
    }
}
