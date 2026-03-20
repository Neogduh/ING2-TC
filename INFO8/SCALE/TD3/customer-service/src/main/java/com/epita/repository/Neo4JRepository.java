package com.epita.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.neo4j.driver.Driver;

import com.epita.controller.contracts.ProductRanking;
import com.epita.controller.contracts.CustomerRanking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class Neo4JRepository {
    @Inject
    Driver neo4jDriver;

    public void recordPurchase(final UUID customerId, final String productName, final int quantity) {
        final var session = neo4jDriver.session();
        session.executeWrite(tx -> tx.run(
                "MERGE (c:Customer {nodeId: $customerId}) " +
                "MERGE (p:Product {nodeId: $productName}) " +
                "MERGE (c)-[r:PURCHASED]->(p) " +
                "SET r.amount = CASE WHEN r.amount IS NULL THEN $quantity ELSE r.amount + $quantity END",
                java.util.Map.of(
                    "customerId", customerId.toString(),
                    "productName", productName,
                    "quantity", quantity
                )
        ));
        session.close();
    }

    public List<ProductRanking> getProductRankings() {
        final var session = neo4jDriver.session();
        final var results = session.executeRead(tx -> tx
                .run("MATCH (p:Product)-[r:PURCHASED]-() " +
                     "RETURN p.nodeId as name, count(distinct r) as customers " +
                     "ORDER BY customers DESC")
                .list());
        session.close();

        if (results.isEmpty()) {
            return List.of();
        }

        List<ProductRanking> rankings = new java.util.ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            final var record = results.get(i);
            rankings.add(new ProductRanking(
                    record.get("name").asString(),
                    i + 1,
                    record.get("customers").asInt()
            ));
        }

        return rankings;
    }

    public Optional<List<CustomerRanking>> getCustomerRankingsForProduct(final String productName) {
        final var session = neo4jDriver.session();

        final var productExists = session.executeRead(tx -> {
            final var result = tx.run("MATCH (p:Product {nodeId: $name}) RETURN p",
                    java.util.Map.of("name", productName)).list();
            return result;
        });
        
        if (productExists.isEmpty()) {
            session.close();
            return Optional.empty();
        }

        final var results = session.executeRead(tx -> tx
                .run("MATCH (c:Customer)-[r:PURCHASED]->(p:Product {nodeId: $name}) " +
                     "RETURN c.nodeId as user, r.amount as amount " +
                     "ORDER BY amount DESC",
                     java.util.Map.of("name", productName))
                .list());
        session.close();

        List<CustomerRanking> rankings = new java.util.ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            final var record = results.get(i);
            rankings.add(new CustomerRanking(
                    UUID.fromString(record.get("user").asString()),
                    i + 1,
                    record.get("amount").asInt()
            ));
        }

        return Optional.of(rankings);
    }
}
