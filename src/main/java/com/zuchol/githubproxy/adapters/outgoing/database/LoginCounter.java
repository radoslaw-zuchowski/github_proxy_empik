package com.zuchol.githubproxy.adapters.outgoing.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "login_counters")
public class LoginCounter {
	@Id
	private String login;
	private Integer requests;

	public LoginCounter() {}

	public LoginCounter(String login, Integer requests) {
		this.login = login;
		this.requests = requests;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getRequests() {
		return requests;
	}

	public void setRequests(Integer requests) {
		this.requests = requests;
	}
}
