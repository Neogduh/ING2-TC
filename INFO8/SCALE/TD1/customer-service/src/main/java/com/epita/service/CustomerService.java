package com.epita.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.epita.controller.contracts.CustomerContract;
import com.epita.controller.contracts.ProductContract;
import com.epita.repository.CustomerRepository;
import com.epita.repository.WarehouseRestClient;
import com.epita.repository.entity.WarehouseProductResponse;
import com.epita.repository.entity.WarehousePurchaseRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerService {
	@Inject
	@RestClient
	WarehouseRestClient warehouseClient;

	@Inject
	CustomerRepository repository;

	public List<ProductContract> getProducts() {
		List<WarehouseProductResponse> products = warehouseClient.getProducts();
		return products.stream().map(p -> new ProductContract(p.name, p.quantity)).toList();
	}

	public Optional<Integer> purchaseProducts(List<ProductContract> products, java.util.UUID userId) {
		try {
			warehouseClient.purchaseProducts(products.stream().map(p -> new WarehousePurchaseRequest(p.name, p.quantity)).toList());
		} catch (ClientWebApplicationException e) {
			return Optional.of(e.getResponse().getStatus());
		}
		
		repository.purchase(userId);

		return Optional.empty();
	}

	public Optional<CustomerContract> getCustomerPurchases(UUID userId) {
		Map<UUID, Integer> customers = repository.getCustomers();
		if (customers.containsKey(userId)) {
			return Optional.of(new CustomerContract(userId, customers.get(userId)));
		}
		return Optional.empty();
	}
}
