package com.epita.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import com.epita.repository.entity.Product;

@ApplicationScoped
public class WarehouseRepository implements PanacheMongoRepositoryBase<Product, String> {
	public List<Product> getProducts() {
		return listAll();
	}
	
	public void restock(String name, Integer quantity) {
		Product product = findById(name);

		if (product != null) {
			update("quantity", product.getQuantity() + quantity).where("_id", name);
		} else {
			Product newProduct = new Product(name, quantity);
			persist(newProduct);
		}
	}

	public Boolean purchase(String name, Integer quantity) {
		Product product = findById(name);
		
		if (product == null || product.getQuantity() < quantity) {
			return false;
		}

		update("quantity", product.getQuantity() - quantity).where("_id", name);
		
		return true;
	}
}
