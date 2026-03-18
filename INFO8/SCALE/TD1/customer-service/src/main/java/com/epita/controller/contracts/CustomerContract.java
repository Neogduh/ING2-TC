package com.epita.controller.contracts;

import java.util.UUID;

public class CustomerContract {
	public UUID customer;
    public Integer purchases;

    public CustomerContract(UUID customer, Integer purchases) {
        this.customer = customer;
        this.purchases = purchases;
    }
}
