package com.epita.repository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class WarehouseRepository {
	private Map<String, Integer> products = new HashMap<>();

	public Map<String, Integer> getProducts() {
		return products;
	}

	public void restock(String name, Integer quantity) {
		if (this.products.containsKey(name)) {
			this.products.put(name, this.products.get(name) + quantity);
		} else {
			this.products.put(name, quantity);
		}
	}

	public Boolean purchase(String name, Integer quantity) {
		Integer currentQuantity = this.products.get(name);
		if (currentQuantity == null || currentQuantity < quantity) {
			return false;
		}

		this.products.put(name, currentQuantity - quantity);
		
		return true;
	}
}
