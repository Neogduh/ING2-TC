package com.epita.controller.contracts;

public class ProductContract {
	public String name;
    public Integer quantity;

    public ProductContract(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
