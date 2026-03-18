package com.epita.controller;

import com.epita.controller.contracts.PurchaseResult;
import com.epita.repository.CustomerRepository;

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
public class PurchaseResultSubscriber implements Consumer<PurchaseResult> {
    @Inject
    CustomerRepository repository;

    private final PubSubCommands.RedisSubscriber subscriber;

    public PurchaseResultSubscriber(final RedisDataSource ds) {
        subscriber = ds.pubsub(PurchaseResult.class)
                .subscribe("purchases", this);
    }

    @Override
    public void accept(final PurchaseResult message) {
        // To keep things simple, we will avoid asynchronous stuff here,
        // so you need to tell Quarkus that you will execute blocking
        // code knowingly, otherwise it may crash at runtime to prevent
        // unwanted blocking code.
        // dispatch the message to service-layer methods here
        vertx.executeBlocking(future -> {
            if (message.type.equals("purchase_result") && message.state == 200)
                repository.createReceipt(message.getUserId(), message.getProducts());

            future.complete();
        });
    }
    
    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
