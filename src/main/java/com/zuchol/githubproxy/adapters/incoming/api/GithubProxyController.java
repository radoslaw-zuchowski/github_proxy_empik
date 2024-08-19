package com.zuchol.githubproxy.adapters.incoming.api;

import com.zuchol.githubproxy.adapters.incoming.api.dto.UserDto;
import com.zuchol.githubproxy.application.GithubProxyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class GithubProxyController {

  private final GithubProxyService githubProxyService;

  public GithubProxyController(GithubProxyService githubProxyService) {
    this.githubProxyService = githubProxyService;
  }

  @GetMapping(path = "{login}")
  ResponseEntity<UserDto> getUserData(@PathVariable String login) {
    var user = githubProxyService.getUserData(login);
    return ResponseEntity.ok(UserDto.mapFromUser(user));
  }
}
