package com.zuchol.githubproxy.adapters.outgoing.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

@Repository
public class CounterRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void getAndUpdateLoginCount(String login) {
		var loginCounter = entityManager.find(LoginCounter.class, login, PESSIMISTIC_WRITE);
		if (loginCounter != null) {
			loginCounter.setRequests(loginCounter.getRequests() + 1);
		} else {
			loginCounter = new LoginCounter(login, 1);
		}
		entityManager.merge(loginCounter);
	}
}
