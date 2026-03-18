package com.epita.repository;

import com.epita.repository.entity.PurchaseCommand;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PurchaseCommandPublisher {
    private final PubSubCommands<PurchaseCommand> publisher;

    public PurchaseCommandPublisher(final RedisDataSource ds) {
        publisher = ds.pubsub(PurchaseCommand.class);
    }
    
    public void publish(final PurchaseCommand message) {
        publisher.publish("purchases", message);
    }
}
