package com.wishlist.cst438project2.integration;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.exception.NotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FirebaseIntegration {

    public final Firestore dbFirestore = FirestoreClient.getFirestore();

    @SneakyThrows
    public UserDTO getUser(String username) {

        log.info("UserServiceImpl: Starting getUser");

        DocumentReference documentReference = dbFirestore.collection(Constants.DOCUMENT_USER).document(username);

        ApiFuture<DocumentSnapshot> snapshotApiFuture = documentReference.get();

        try {

            DocumentSnapshot documentSnapshot = snapshotApiFuture.get();

            User user;
            if (documentSnapshot.exists()) {
                user = documentSnapshot.toObject(User.class);
            } else {
                throw new NotFoundException(Constants.ERROR_USER_NOT_FOUND);
            }

            log.info("UserServiceImpl: Exiting getUser");

            return user.fetchUserDTO();

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @SneakyThrows
    public ItemDTO getItem(String name) {
        log.info("FirebaseIntegration: Starting getItem");
        DocumentReference documentReference = dbFirestore.collection(Constants.DOCUMENT_ITEM).document(name);
        ApiFuture<DocumentSnapshot> snapshotApiFuture = documentReference.get();

        try {
            DocumentSnapshot documentSnapshot = snapshotApiFuture.get();
            Item item;

            if (documentSnapshot.exists()) {
                item = documentSnapshot.toObject(Item.class);
            } else {
                throw new NotFoundException(Constants.ERROR_ITEM_NOT_FOUND);
            }
            log.info("FirebaseIntegration: Exiting getItem");
            return item.fetchItemDTO();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
