package com.epita.controller;

import com.epita.controller.contracts.PurchaseCommand;
import com.epita.service.WarehouseService;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Consumer;

import static io.quarkus.mongodb.runtime.dns.MongoDnsClientProvider.vertx;

@Startup
@ApplicationScoped
public class PurchaseCommandSubscriber implements Consumer<PurchaseCommand> {
    @Inject
    WarehouseService service;

    private final PubSubCommands.RedisSubscriber subscriber;

    public PurchaseCommandSubscriber(final RedisDataSource ds) {
        subscriber = ds.pubsub(PurchaseCommand.class)
                .subscribe("purchases", this);
    }

    @Override
    public void accept(final PurchaseCommand message) {
        // To keep things simple, we will avoid asynchronous stuff here,
        // so you need to tell Quarkus that you will execute blocking
        // code knowingly, otherwise it may crash at runtime to prevent
        // unwanted blocking code.
        // dispatch the message to service-layer methods here
        vertx.executeBlocking(future -> {
            if (message.type.equals("purchase"))
                service.purchaseProducts(message.getUserId(), message.getProducts());

            future.complete();
        });
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
