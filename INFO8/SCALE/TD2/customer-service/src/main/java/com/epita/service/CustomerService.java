package com.epita.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.epita.controller.contracts.CustomerContract;
import com.epita.controller.contracts.ProductContract;
import com.epita.repository.CustomerRepository;
import com.epita.repository.PurchaseCommandPublisher;
import com.epita.repository.WarehouseRestClient;
import com.epita.repository.entity.WarehouseProductResponse;
import com.epita.repository.entity.PurchaseCommand;
import com.epita.repository.entity.PurchaseReceipt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerService {
	@Inject
	@RestClient
	WarehouseRestClient warehouseClient;

	@Inject
	CustomerRepository repository;

	@Inject
	PurchaseCommandPublisher publisher;

	public List<ProductContract> getProducts() {
		List<WarehouseProductResponse> products = warehouseClient.getProducts();
		return products.stream().map(p -> new ProductContract(p.name, p.quantity)).toList();
	}

	public Optional<Integer> purchaseProducts(List<ProductContract> products, UUID userId) {
		PurchaseCommand result = new PurchaseCommand(userId, products);
        List<WarehouseProductResponse> currentProducts = warehouseClient.getProducts();

        for (ProductContract product : products) {
            if (product.name == null || product.name.isEmpty() || product.quantity <= 0) {
                return Optional.of(400);
            }

            Optional<WarehouseProductResponse> productOptional = currentProducts.stream()
                    .filter(p -> p.name.equals(product.name))
                    .findFirst();
            if (productOptional.isEmpty()) {
                return Optional.of(404);
            }
            if (productOptional.get().quantity < product.quantity) {
                return Optional.of(400);
            }
        }

        publisher.publish(result);
        return Optional.empty();
	}

	public Optional<CustomerContract> getCustomerPurchases(UUID userId) {
		List<PurchaseReceipt> receipt = repository.getReceiptsByUserId(userId);
		if (receipt == null || receipt.isEmpty()) {
			return Optional.empty();
		}

		System.out.println("Receipts for user " + userId + ":");
		receipt.forEach(r -> System.out.println(r.getId() + ", " + r.getProducts() + ", " + r.getUserId()));

		return Optional.of(new CustomerContract(userId, receipt.stream().map(PurchaseReceipt::getId).toList(), receipt.size()));
	}

	public Optional<PurchaseReceipt> getPurchaseById(UUID purchaseId) {
		PurchaseReceipt receipt = repository.getReceiptById(purchaseId);
		if (receipt == null) {
			return Optional.empty();
		}

		return Optional.of(receipt);
	}
}
