package com.wishlist.cst438project2.integration;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.exception.NotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class FirebaseIntegration {

    public final Firestore dbFirestore = FirestoreClient.getFirestore();

    @SneakyThrows
    public UserDTO getUser(String username) {

        log.info("FirebaseIntegration: Starting getUser");

        DocumentReference documentReference = dbFirestore.collection(Constants.DOCUMENT_USER).document(username);

        ApiFuture<DocumentSnapshot> snapshotApiFuture = documentReference.get();

        try {

            DocumentSnapshot documentSnapshot = snapshotApiFuture.get();

            User user = null;
            if (documentSnapshot.exists()) {
                user = documentSnapshot.toObject(User.class);
            }

            log.info("FirebaseIntegration: Exiting getUser");

            return user == null ? null : user.fetchUserDTO();

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @SneakyThrows
    public List<UserDTO> getAllUsers() {

        log.info("FirebaseIntegration: Starting getAllUsers");

        List<UserDTO> userDTOList = new ArrayList<>();

        try {

            ApiFuture<QuerySnapshot> future = dbFirestore.collection(Constants.DOCUMENT_USER).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            if(!documents.isEmpty()) {

                log.info("User count: " + documents.size());

                for(QueryDocumentSnapshot document: documents) {
                    userDTOList.add(document.toObject(UserDTO.class));
                }
            }

            log.info("FirebaseIntegration: Exiting getAllUsers");

            return userDTOList;

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @SneakyThrows
    public void deleteUser(String username) {

        log.info("FirebaseIntegration: Starting deleteUser");

        try {

            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(Constants.DOCUMENT_USER).document(username).delete();

            String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

            log.info(Constants.USER_DELETED + " {}" , responseTimestamp);

            log.info("FirebaseIntegration: Exiting deleteUser");

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
