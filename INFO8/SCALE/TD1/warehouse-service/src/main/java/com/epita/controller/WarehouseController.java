package com.epita.controller;

import java.util.List;
import java.util.Optional;

import com.epita.controller.contracts.ProductContract;
import com.epita.service.WarehouseService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class WarehouseController {
    @Inject
    WarehouseService service;

    @GET
    @Path("products")
    public Response getProducts() {
        return Response.ok(service.getProducts()).build();
    }

    @GET
    @Path("products/{name}")
    public Response getProductByName(@PathParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ProductContract product = service.getProductByName(name);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(product).build();
    }

    @POST
    @Path("purchase")
    public Response purchaseProduct(List<ProductContract> products) {
        if (products == null || products.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<Integer> success = service.purchaseProducts(products);

        if (success.isPresent() && success.get() == 404) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (success.isPresent() && success.get() == 400) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("restock")
    public Response restockProduct(List<ProductContract> products) {
        if (products == null || products.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(!service.restockProduct(products)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }
}
