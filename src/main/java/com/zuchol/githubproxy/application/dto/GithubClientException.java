package com.zuchol.githubproxy.application.dto;

public class GithubClientException extends RuntimeException {
    public GithubClientException(String message) {
        super(message);
    }
}
