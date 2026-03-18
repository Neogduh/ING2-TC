package com.epita.repository;

import com.epita.repository.entity.CommandResult;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PurchaseResultPublisher {
    private final PubSubCommands<CommandResult> publisher;

    public PurchaseResultPublisher(final RedisDataSource ds) {
        publisher = ds.pubsub(CommandResult.class);
    }
    
    public void publish(final CommandResult message) {
        publisher.publish("purchases", message);
    }
}
