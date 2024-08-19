package com.zuchol.githubproxy.application.dto;

public class LoginNotFoundException extends RuntimeException {

    public LoginNotFoundException(String message) {
        super(message);
    }
}
