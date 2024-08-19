package com.zuchol.githubproxy.adapters.incoming.api.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        int statusCode,
        String userMessage,
        LocalDateTime timestamp,
        String errorDetails
) {
    public ErrorResponse(int statusCode, String userMessage, String errorDetails) {
        this(statusCode, userMessage, LocalDateTime.now(), errorDetails);
    }
}
