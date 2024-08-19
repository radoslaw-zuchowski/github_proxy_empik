package com.zuchol.githubproxy.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    StringBuilder logMessage = new StringBuilder();
    logMessage.append("Request URL: ").append(request.getRequestURL()).append("\n");
    logMessage.append("Request Method: ").append(request.getMethod()).append("\n");
    logHeaders(request, logMessage);
    logPayload(request, logMessage);
    logger.info(logMessage.toString());
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    StringBuilder logMessage = new StringBuilder();
    logMessage.append("Response Status: ").append(response.getStatus()).append("\n");
    logHeaders(response, logMessage);
    logResponsePayload(response, logMessage);
    logger.info(logMessage.toString());
  }

  private void logHeaders(HttpServletRequest request, StringBuilder logMessage) {
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      logMessage
          .append("Request Header: ")
          .append(headerName)
          .append(" = ")
          .append(request.getHeader(headerName))
          .append("\n");
    }
  }

  private void logHeaders(HttpServletResponse response, StringBuilder logMessage) {
    response
        .getHeaderNames()
        .forEach(
            headerName ->
                logMessage
                    .append("Response Header: ")
                    .append(headerName)
                    .append(" = ")
                    .append(response.getHeader(headerName))
                    .append("\n"));
  }

  private void logPayload(HttpServletRequest request, StringBuilder logMessage) throws IOException {
    String payload =
        request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    if (!payload.isEmpty()) {
      logMessage.append("Request Payload: ").append(payload).append("\n");
    }
  }

  private void logResponsePayload(HttpServletResponse response, StringBuilder logMessage)
      throws IOException {}
}
