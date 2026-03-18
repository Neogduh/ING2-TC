package com.epita.repository.entity;

import com.epita.controller.contracts.ProductContract;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PurchaseCommand {
    public String type;
    public UUID userId;
    public List<ProductContract> products;

    public PurchaseCommand(UUID userId, List<ProductContract> products) {
        this.type = "purchase";
        this.userId = userId;
        this.products = products;
    }
}
