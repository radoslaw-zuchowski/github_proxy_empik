package com.zuchol.githubproxy.application.dto;

public record UserWithCalculations(
    String id,
    String login,
    String name,
    String type,
    String avatarUrl,
    String createdAt,
    Double calculations) {}
