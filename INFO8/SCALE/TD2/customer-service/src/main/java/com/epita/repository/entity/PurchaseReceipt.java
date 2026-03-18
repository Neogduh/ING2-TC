package com.epita.repository.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import com.epita.controller.contracts.ProductContract;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MongoEntity(collection = "Purchases")
public class PurchaseReceipt extends PanacheMongoEntityBase {
	@BsonId
	public UUID id;

	public UUID userId;

	public List<ProductContract> products;

	public PurchaseReceipt(UUID userId, List<ProductContract> products) {
		this.id = UUID.randomUUID();
		this.userId = userId;
		this.products = products;
	}
}
