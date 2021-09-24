package com.wishlist.cst438project2.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.wishlist.cst438project2.Common.Constants;
import com.wishlist.cst438project2.document.Product;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    private final Firestore dbFirestore = FirestoreClient.getFirestore();

    @SneakyThrows
    public String saveProduct(Product product) {

        log.info("ProductServiceImpl: Starting saveProduct");

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(Constants.DOCUMENT_PRODUCT).document(product.getName()).set(product);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        log.info("ResponseTimestamp: {}", responseTimestamp);

        log.info("ProductServiceImpl: Exiting saveProduct");

        return responseTimestamp;
    }
}
