package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.wishlist.cst438project2.Common.Constants;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.exception.NotFoundException;
import com.wishlist.cst438project2.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final Firestore dbFirestore = FirestoreClient.getFirestore();

    @SneakyThrows
    @Override
    public String saveUser(User user) {

        log.info("UserServiceImpl: Starting saveUser");

        //user.setPassword(Utils.encodePassword(user.getPassword()));

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(Constants.DOCUMENT_USER).document(user.getUsername()).set(user);

        String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

        log.info("ResponseTimestamp: {}", responseTimestamp);

        log.info("UserServiceImpl: Exiting saveUser");

        return responseTimestamp;
    }

    @SneakyThrows
    @Override
    public User getUser(String username) {

        log.info("UserServiceImpl: Starting getUser");

        DocumentReference documentReference = dbFirestore.collection(Constants.DOCUMENT_USER).document(username);

        ApiFuture<DocumentSnapshot> snapshotApiFuture = documentReference.get();

        try {

            DocumentSnapshot documentSnapshot = snapshotApiFuture.get();

            User user;
            if(documentSnapshot.exists())
                user = documentSnapshot.toObject(User.class);
            else
                throw new NotFoundException(Constants.ERROR_USER_NOT_FOUND);

            log.info("UserServiceImpl: Exiting getUser");

            return user;

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
