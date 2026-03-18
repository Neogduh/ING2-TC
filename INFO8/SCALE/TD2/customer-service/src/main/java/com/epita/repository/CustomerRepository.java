package com.epita.repository;

import java.util.List;
import java.util.UUID;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import com.epita.controller.contracts.ProductContract;
import com.epita.repository.entity.PurchaseReceipt;

@ApplicationScoped
public class CustomerRepository implements PanacheMongoRepositoryBase<PurchaseReceipt, UUID> {
	public List<PurchaseReceipt> getReceipts() {
		return listAll();
	}

	public PurchaseReceipt getReceiptById(UUID id) {
		return findById(id);
	}

	public List<PurchaseReceipt> getReceiptsByUserId(UUID userId) {
		return list("userId", userId);
	}

	public void createReceipt(UUID userId, List<ProductContract> products) {
		PurchaseReceipt newReceipt = new PurchaseReceipt(userId, products);
		persist(newReceipt);
	}
}
