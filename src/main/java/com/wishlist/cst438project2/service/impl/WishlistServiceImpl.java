package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.document.Wishlist;
import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import com.wishlist.cst438project2.service.WishlistService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    @SneakyThrows
    @Override
    public Wishlist addItemsWishlist(AddItemsWishlistDTO addItemsWishlistDTO) {

        log.info("WishlistServiceImpl: Starting addItemsWishlist");

        Wishlist dbWishlist = fetchWishlistByUser(addItemsWishlistDTO.getUserId());

        if(Objects.isNull(dbWishlist))
            dbWishlist = new Wishlist(addItemsWishlistDTO.getUserId(), addItemsWishlistDTO.getItemIds());
        else {

            List<String> itemIds = dbWishlist.getItemIds();
            itemIds.addAll(addItemsWishlistDTO.getItemIds());
            dbWishlist.setItemIds(itemIds);
        }

        ApiFuture<WriteResult> collectionApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_USER_WISHLIST).document(addItemsWishlistDTO.getUserId()).set(dbWishlist);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        log.info("WishlistServiceImpl: Exiting addItemsWishlist");

        if(!responseTimestamp.isEmpty()) {
             return dbWishlist;
        }

        return null;
    }

    private Wishlist fetchWishlistByUser(String userId) {

        Wishlist wishlist = firebaseIntegration.getUserWishlist(userId);

        if(Objects.isNull(wishlist)) {
            return null;
        }

        return wishlist;
    }
}
