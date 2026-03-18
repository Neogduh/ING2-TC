package com.epita.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository {
	private Map<UUID, Integer> customers = new HashMap<>();

	public Map<UUID, Integer> getCustomers() {
		return customers;
	}

	public void purchase(UUID userId) {
		if (customers.containsKey(userId)) {
			customers.put(userId, customers.get(userId) + 1);
		} else {
			customers.put(userId, 1);
		}
	}
}
