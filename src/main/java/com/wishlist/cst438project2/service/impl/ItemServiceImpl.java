package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.document.Item;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.exception.ExternalServerException;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import com.wishlist.cst438project2.service.ItemService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ItemServiceImpl implements ItemService {

       @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FirebaseIntegration firebaseIntegration;
    private final Firestore dbFirestore = FirestoreClient.getFirestore();

    /**
     * database record creation route for item. Each new item will be set to an auto-incremented id
     * <p>
     * returns timestamp of record creation.
     */
    @SneakyThrows
    @Override
    public String createItem(Item item) {
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(Constants.DOCUMENT_ITEM).document().set(item);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
}
