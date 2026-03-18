package com.epita.controller.contracts;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PurchaseResult {
    public String type;
    public UUID userId;
    public List<ProductContract> products;
    public Integer state;

    public PurchaseResult(UUID userId, List<ProductContract> products, Integer state) {
        this.type = "purchase_result";
        this.userId = userId;
        this.products = products;
        this.state = state;
    }
}
