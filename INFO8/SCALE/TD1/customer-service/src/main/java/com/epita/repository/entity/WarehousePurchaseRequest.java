package com.epita.repository.entity;

public class WarehousePurchaseRequest {
	public String name;
	public Integer quantity;

	public WarehousePurchaseRequest(String name, Integer quantity) {
		this.name = name;
		this.quantity = quantity;
	}
}
