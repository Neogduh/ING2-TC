package com.epita.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.epita.repository.entity.PurchaseReceipt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ElasticSearchRepository {
    @Inject
    ElasticsearchClient elasticsearchClient;

    public void createPurchaseReceipt(PurchaseReceipt purchaseReceipt) throws IOException {
        elasticsearchClient.index(indexRequestBuilder -> indexRequestBuilder
                .index("purchases")
                .id(UUID.randomUUID().toString())
                .document(purchaseReceipt)
                .refresh(co.elastic.clients.elasticsearch._types.Refresh.True)
        );
    }

    public List<PurchaseReceipt> searchPurchases(final UUID userId, final List<String> productTerms) throws IOException {
        final BoolQuery.Builder bool = new BoolQuery.Builder();

        if (userId != null) {
            bool.filter(q -> q.term(t -> t.field("userId.keyword").value(userId.toString())));
        }

        if (productTerms != null && !productTerms.isEmpty()) {
            for (final var term : productTerms) {
                if (term == null) continue;
                final var trimmed = term.trim();
                if (trimmed.isEmpty()) continue;

                bool.should(q -> q.match(m -> m
                        .field("products.name")
                        .query(trimmed)
                        .operator(Operator.And)
                ));
            }
        }

        final Query query = new Query.Builder().bool(bool.build()).build();

        final SearchResponse<PurchaseReceipt> response = elasticsearchClient.search(s -> s
                        .index("purchases")
                        .query(query)
                        .size(1000),
                PurchaseReceipt.class);

        final List<PurchaseReceipt> out = new ArrayList<>();

        for (final Hit<PurchaseReceipt> hit : response.hits().hits()) {
            if (hit.source() != null) {
                out.add(hit.source());
            }
        }
        return out;
    }
}
