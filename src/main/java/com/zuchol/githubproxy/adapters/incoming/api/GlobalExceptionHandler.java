package com.zuchol.githubproxy.adapters.incoming.api;


import com.zuchol.githubproxy.adapters.incoming.api.dto.ErrorResponse;
import com.zuchol.githubproxy.application.dto.GithubClientException;
import com.zuchol.githubproxy.application.dto.LoginNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoginNotFoundException(LoginNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                NOT_FOUND.value(),
                "The login was not found in the system",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(GithubClientException.class)
    public ResponseEntity<ErrorResponse> handleGithubClientException(GithubClientException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }
}
