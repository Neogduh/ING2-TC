package com.epita.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.epita.controller.contracts.ProductContract;
import com.epita.repository.WarehouseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WarehouseService {
    @Inject
    WarehouseRepository repository;

    public List<ProductContract> getProducts() {
        Map<String, Integer> products = repository.getProducts();
        return products.entrySet().stream()
                .map(entry -> new ProductContract(entry.getKey(), entry.getValue()))
                .toList();
    }

    public ProductContract getProductByName(String name) {
        Map<String, Integer> products = repository.getProducts();
        return products.entrySet().stream()
                .filter(entry -> entry.getKey().equals(name))
                .findFirst()
                .map(entry -> new ProductContract(entry.getKey(), entry.getValue()))
                .orElse(null);
    }

    public Optional<Integer> purchaseProducts(List<ProductContract> products) {
        Map<String, Integer> currentProducts = repository.getProducts();

        for (ProductContract product : products) {
            if (product.name == null || product.name.isEmpty() || product.quantity <= 0)
                return Optional.of(400);

            Integer availableQuantity = currentProducts.get(product.name);
            if (availableQuantity == null)
                return Optional.of(404);
            if (availableQuantity < product.quantity)
                return Optional.of(400);
        }

        for (ProductContract product : products) {
            repository.purchase(product.name, product.quantity);
        }

        return Optional.empty();
    }

    public Boolean restockProduct(List<ProductContract> products) {
        for (ProductContract product : products) {
            if (product.name == null || product.name.isEmpty() || product.quantity <= 0) {
                return false;
            }
        }

        for (ProductContract product : products) {
            repository.restock(product.name, product.quantity);
        }

        return true;
    }
}
