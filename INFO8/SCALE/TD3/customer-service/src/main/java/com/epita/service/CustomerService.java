package com.epita.service;

import com.epita.controller.contracts.CustomerContract;
import com.epita.controller.contracts.ProductRanking;
import com.epita.controller.contracts.CustomerRanking;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.epita.controller.contracts.ProductContract;
import com.epita.repository.entity.PurchaseReceipt;
import com.epita.repository.ElasticSearchRepository;
import com.epita.repository.Neo4JRepository;
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
	ElasticSearchRepository elasticSearch;

	@Inject
	Neo4JRepository neo4j;

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
		
		try {
			elasticSearch.createPurchaseReceipt(new PurchaseReceipt(userId, products));
		} catch (Exception e) {
			return Optional.of(500);
		}

		for (ProductContract product : products) {
			neo4j.recordPurchase(userId, product.name, product.quantity);
		}

		return Optional.empty();
	}

	public Optional<List<PurchaseReceipt>> searchPurchases(UUID userId, String productQuery) {
		try {
			List<String> terms;
			if (productQuery == null || productQuery.isBlank()) {
				terms = List.of();
			} else {
				terms = Arrays.stream(productQuery.split("\\s+"))
						.map(String::trim)
						.filter(s -> !s.isEmpty())
						.toList();
			}

			return Optional.of(elasticSearch.searchPurchases(userId, terms));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public Optional<CustomerContract> getCustomerPurchases(UUID userId) {
		Optional<List<PurchaseReceipt>> userPurchases = searchPurchases(userId, "");
		if (userPurchases.isEmpty())
			return Optional.empty();
		return Optional.of(new CustomerContract(userId, userPurchases.get().size()));
	}

	public List<ProductRanking> getProductsRankedByCustomers() {
		List<ProductRanking> products = neo4j.getProductRankings();
		List<WarehouseProductResponse> allProducts = warehouseClient.getProducts();

		for (WarehouseProductResponse product : allProducts) {
			if (!products.stream().map(p -> p.name).toList().contains(product.name))
				products.add(new ProductRanking(product.name, products.size() + 1, 0));
		}

		return products;
	}

	public Optional<List<CustomerRanking>> getCustomersRankedByPurchases(String productName) {
		return neo4j.getCustomerRankingsForProduct(productName);
	}
}
