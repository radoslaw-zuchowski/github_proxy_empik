package com.zuchol.githubproxy.adapters.outgoing.github;

public record GithubUserDto(
    String id,
    String login,
    String name,
    String type,
    String avatar_url,
    String created_at,
    Integer followers,
    Integer public_repos
) {}
