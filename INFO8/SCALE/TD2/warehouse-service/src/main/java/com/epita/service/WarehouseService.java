package com.epita.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.epita.controller.contracts.ProductContract;
import com.epita.repository.WarehouseRepository;
import com.epita.repository.PurchaseResultPublisher;
import com.epita.repository.entity.CommandResult;
import com.epita.repository.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WarehouseService {
    @Inject
    WarehouseRepository repository;

    @Inject
    PurchaseResultPublisher publisher;

    public List<ProductContract> getProducts() {
        List<Product> products = repository.getProducts();
        return products.stream()
                .map(p -> new ProductContract(p.name, p.quantity))
                .toList();
    }

    public ProductContract getProductByName(String name) {
        List<Product> products = repository.getProducts();
        return products.stream()
                .filter(p -> p.name.equals(name))
                .findFirst()
                .map(p -> new ProductContract(p.name, p.quantity))
                .orElse(null);
    }

    public void purchaseProducts(UUID userId, List<ProductContract> products) {
        CommandResult result = new CommandResult(userId, products, 200);
        List<Product> currentProducts = repository.getProducts();

        for (ProductContract product : products) {
            if (product.name == null || product.name.isEmpty() || product.quantity <= 0) {
                result.setState(400);
                publisher.publish(result);
                return;
            }

            Optional<Product> productOptional = currentProducts.stream()
                    .filter(p -> p.name.equals(product.name))
                    .findFirst();
            if (productOptional.isEmpty()) {
                result.setState(404);
                publisher.publish(result);
                return;
            }
            if (productOptional.get().quantity < product.quantity) {
                result.setState(400);
                publisher.publish(result);
                return;
            }
        }

        for (ProductContract product : products) {
            repository.purchase(product.name, product.quantity);
        }

        publisher.publish(result);
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
