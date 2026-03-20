package com.epita.repository.entity;

import com.epita.controller.contracts.ProductContract;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseReceipt {
	public UUID userId;
	public List<ProductContract> products;
}
