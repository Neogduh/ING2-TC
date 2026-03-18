package com.epita.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.epita.controller.contracts.CustomerContract;
import com.epita.controller.contracts.ProductContract;
import com.epita.service.CustomerService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class CustomerController {
	@Inject
	CustomerService service;

	@GET
	@Path("products")
	public Response getProducts() {
		return Response.ok(service.getProducts()).build();
	}

	@POST
	@Path("purchase")
	public Response purchaseProducts(List<ProductContract> products, @HeaderParam("X-user-id") UUID userId) {
		
		if (products == null || products.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (userId == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Missing X-user-id header").build();
		}
		
		Optional<Integer> success = service.purchaseProducts(products, userId);

		if (success.isPresent() && success.get() == 404) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (success.isPresent() && success.get() == 400) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

		return Response.ok().build();
	}

	@GET
	@Path("purchases")
	public Response getCustomerPurchases(@HeaderParam("X-user-id") UUID userId) {

		if (userId == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Missing X-user-id header").build();
		}

		Optional<CustomerContract> customer = service.getCustomerPurchases(userId);
		if (customer.isPresent()) {
			return Response.ok(customer.get()).build();
		}
		
		return Response.status(Response.Status.NOT_FOUND).build();
	}
}
