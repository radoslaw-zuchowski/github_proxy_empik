package com.zuchol.githubproxy.application.dto;

public record User(
    String id,
    String login,
    String name,
    String type,
    String avatarUrl,
    String createdAt,
    Integer followers,
    Integer publicRepos) {}
