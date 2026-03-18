package com.epita.repository.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import com.epita.controller.contracts.ProductContract;

@Getter
@Setter
public class CommandResult {
    public String type;
    public UUID userId;
    public List<ProductContract> products;
    public Integer state;

    public CommandResult(UUID userId, List<ProductContract> products, Integer state) {
        this.type = "purchase_result";
        this.userId = userId;
        this.products = products;
        this.state = state;
    }
}
