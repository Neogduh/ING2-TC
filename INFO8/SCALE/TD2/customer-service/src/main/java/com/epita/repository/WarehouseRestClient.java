package com.epita.repository;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.epita.repository.entity.WarehouseProductResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey="warehouse-service")
@Path("/api")
public interface WarehouseRestClient {
	@GET
	@Path("/products")
	List<WarehouseProductResponse> getProducts();

	@POST
    @Path("restock")
	Response restockProducts(List<WarehouseProductResponse> products);
}
