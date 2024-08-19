package com.zuchol.githubproxy.adapters.incoming.api.dto;

import com.zuchol.githubproxy.application.dto.UserWithCalculations;

public record UserDto(
    String id,
    String login,
    String name,
    String type,
    String avatarUrl,
    String createdAt,
    Double calculations) {
  public static UserDto mapFromUser(UserWithCalculations user) {
    return new UserDto(
        user.id(),
        user.login(),
        user.name(),
        user.type(),
        user.avatarUrl(),
        user.createdAt(),
        user.calculations());
  }
}
