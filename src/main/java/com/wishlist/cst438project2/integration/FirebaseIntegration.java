package com.wishlist.cst438project2.integration;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.document.AccessToken;
import com.wishlist.cst438project2.document.Item;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.document.Wishlist;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FirebaseIntegration {

    public final Firestore dbFirestore = FirestoreClient.getFirestore();

    @Autowired
    private ModelMapper modelMapper;

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

            return user == null ? null : modelMapper.map(user, UserDTO.class);

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

    /**
     * helper method for item endpoints
     * returns int userId for a given String username
     */
    @SneakyThrows
    public long getUserId(String username) {
        UserDTO user = getUser(username);
        return user.getUserId();
    }

    /**
     * returns the db item that matches given item name and userId or null if not found
     * <p>
     * NOTE: some items may have the same name but be connected to different users
     * @param name item name to be matched against Item db
     */
    @SneakyThrows
    public ItemDTO getItem(String name, long userId) {
        log.info("FirebaseIntegration: Starting getItem");
        CollectionReference collectionReference = dbFirestore.collection(Constants.DOCUMENT_ITEM);
        Query query = collectionReference.whereEqualTo(Constants.FIELD_ITEM_NAME, name).whereEqualTo(Constants.FIELD_USER_ID, userId);
        ApiFuture<QuerySnapshot> snapshotApiFuture = query.get();

        try {
            QuerySnapshot querySnapshot = snapshotApiFuture.get();
            Item item = null;

            // if item exists, return the item?
            if (querySnapshot.size() > 0) {
                log.info("\nFirebaseIntegration: createItem: querySnapshot:");
                log.info(querySnapshot.toString());
                item = querySnapshot.toObjects(Item.class).get(0);
            }

            log.info("FirebaseIntegration: Exiting getItem");
            return item == null ? null : item.fetchItemDTO();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * return the item document id associated with a given item name and user ID
     * @param name item name
     * @param userId
     */
    @SneakyThrows
    public String getItemDocId(String name, long userId) {
        log.info("FirebaseIntegration: Starting getItemDocId");
        CollectionReference collectionReference = dbFirestore.collection(Constants.DOCUMENT_ITEM);
        Query query = collectionReference.whereEqualTo(Constants.FIELD_ITEM_NAME, name).whereEqualTo(Constants.FIELD_USER_ID, userId);
        ApiFuture<QuerySnapshot> snapshotApiFuture = query.get();

        try {
            QuerySnapshot querySnapshot = snapshotApiFuture.get();
//            log.info(String.format("FirebaseIntegration: getItemDocId:\n    querySnapshot.size(): %d", querySnapshot.size()));

            if ((querySnapshot.size() < 1) || (querySnapshot.size() > 1)) {
                throw new BadRequestException(Constants.ERROR_ITEM_NOT_FOUND);
            }

            log.info(String.format("FirebaseIntegration: getItemDocId: %s", querySnapshot.getDocuments().get(0).getId()));
            log.info("FirebaseIntegration: Exiting getItemDocId");
            return querySnapshot.getDocuments().get(0).getId();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * returns list of all documents within item collection
     */
    @SneakyThrows
    public List<ItemDTO> getAllItems() {
        log.info("FirebaseIntegration: Starting getAllItems");
        List<ItemDTO> collection = new ArrayList<>();

        try {
            ApiFuture<QuerySnapshot> dbNudge = dbFirestore.collection(Constants.DOCUMENT_ITEM).get();
            List<QueryDocumentSnapshot> documents = dbNudge.get().getDocuments();

            for(QueryDocumentSnapshot snap : documents) {
                collection.add(snap.toObject(ItemDTO.class));
            }

            log.info("FirebaseIntegration: Exiting getAllItems");
            return collection;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * remove the item associated with a given document ID
     * returns timestamp of deletion
     */
    @SneakyThrows
    public String removeItem(String docId) {
        log.info("FirebaseIntegration: Starting removeItem");
        try {
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(Constants.DOCUMENT_ITEM).document(docId).delete();
            String responseTimestamp = writeResult.get().getUpdateTime().toString();
            log.info(Constants.ITEM_REMOVED + " {}" , responseTimestamp);

            log.info("FirebaseIntegration: Exiting removeItem");
            return responseTimestamp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * update the item associated with a given document ID
     * returns timestamp of successful update
     */
    @SneakyThrows
    public String updateItem(String docId, ItemDTO updatedItemDTO) {
        log.info("FirebaseIntegration: Starting updateItem");
        try {
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(Constants.DOCUMENT_ITEM)
                                                            .document(docId).set(updatedItemDTO);
            String responseTimestamp = writeResult.get().getUpdateTime().toString();
            log.info(Constants.ITEM_UPDATED + " {}" , responseTimestamp);

            log.info("FirebaseIntegration: Exiting updateItem");
            return responseTimestamp;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * retrieve a list of items given a user's id
     * returns list of items
     */
    @SneakyThrows
    public List<ItemDTO> getUserItems(String username) {
        log.info("FirebaseIntegration: Starting getUserItems");
        long userId = getUserId(username);
        List<ItemDTO> userItems = new ArrayList<>();

        CollectionReference collectionReference = dbFirestore.collection(Constants.DOCUMENT_ITEM);
        Query query = collectionReference.whereEqualTo(Constants.FIELD_USER_ID, userId);
        ApiFuture<QuerySnapshot> snapshotApiFuture = query.get();

        try {
            QuerySnapshot querySnapshot = snapshotApiFuture.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for(QueryDocumentSnapshot doc : documents) {
                userItems.add(doc.toObject(ItemDTO.class));
            }

            return userItems;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * retrieve a list of items based on search keywords within name and description
     * returns list of items
     */
    @SneakyThrows
    public List<ItemDTO> getSearchAllItems(List<String> keywords) {
        log.info("FirebaseIntegration: Starting getSearchItems");
        List<ItemDTO> allItems = getAllItems();
        List<ItemDTO> searchItems = new ArrayList<>();

        for (ItemDTO item : allItems) {
            if (keywordsPresent(item, keywords)) {
                searchItems.add(item);
            }
        }
        return searchItems;
    }
    /**
     * utility function to search for a list of keywords in a given item
     * @param keywords refers to list of Strings to look for in an item's name or description
     * returns true if one or more keywords are present, false in none are present
     */
    private boolean keywordsPresent(ItemDTO item, List<String> keywords) {
        log.info("FirebaseIntegration: Starting keywordsPresent");
        boolean found = false;

        for (String keyword : keywords) {
            log.info(String.format("\n    keyword: %s\n    item name: %s\n    item description: %s",
                    keyword, item.getName(), item.getDescription()));
            if (item.getName().toLowerCase().contains(keyword)) {
                found = true;
                break;
            }
            if (item.getDescription() != null && item.getDescription().toLowerCase().contains(keyword)) {
                found = true;
                break;
            }
        }
        log.info("FirebaseIntegration: Exiting keywordsPresent");
        return found;
    }

    @SneakyThrows
    public String removeItemsByUser(String username) {
        log.info("FirebaseIntegration: Starting removeItemsByUser");
        long userId = getUserId(username);
        String timestamp = "";

        try {
            List<ItemDTO> userItems = getUserItems(username);
            int removed = 0;
            log.info("FirebaseIntegration: removeItemsByUser\n    userItems size: {}", userItems.size());
            for (int i = 0; i < userItems.size(); i++) {
                String docId = getItemDocId(userItems.get(i).getName(), userId);
                if (i == userItems.size() - 1) {
                    timestamp = removeItem(docId);
                } else {
                    removeItem(docId);
                }
                removed++;
            }
            log.info("FirebaseIntegration: removeItemsByUser\n    # items removed: {}", removed);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            throw ex;
        }

        log.info("FirebaseIntegration: Exiting removeItemsByUser");
        return timestamp;
    }


    @SneakyThrows
    public Wishlist getUserWishlist(Long userId) {

        log.info("FirebaseIntegration: Starting getUserWishlist for User: {}", userId);

        DocumentReference documentReference = dbFirestore.collection(Constants.DOCUMENT_USER_WISHLIST).document(String.valueOf(userId));

        ApiFuture<DocumentSnapshot> snapshotApiFuture = documentReference.get();

        try {

            DocumentSnapshot documentSnapshot = snapshotApiFuture.get();

            Wishlist wishlist = null;
            if (documentSnapshot.exists()) {
                wishlist = documentSnapshot.toObject(Wishlist.class);
            }

            log.info("FirebaseIntegration: Exiting getUserWishlist");
            return wishlist;

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @SneakyThrows
    public void saveAccessToken(AccessToken accessToken) {

        log.info("FirebaseIntegration: Starting saveAccessToken");

        try {
            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(Constants.DOCUMENT_ACCESS_TOKEN).document(accessToken.getToken()).set(accessToken);

            String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

            log.info("ResponseTimestamp: {}", responseTimestamp);

            log.info("FirebaseIntegration: Exiting saveAccessToken");

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @SneakyThrows
    public AccessToken fetchAccessToken(String token) {

        log.info("FirebaseIntegration: Starting fetchAccessToken");

        try {
            DocumentReference documentReference = dbFirestore.collection(Constants.DOCUMENT_ACCESS_TOKEN).document(token);

            ApiFuture<DocumentSnapshot> snapshotApiFuture = documentReference.get();

            DocumentSnapshot documentSnapshot = snapshotApiFuture.get();

            AccessToken accessToken = null;
            if (documentSnapshot.exists()) {
                accessToken = documentSnapshot.toObject(AccessToken.class);
            }

            log.info("FirebaseIntegration: Exiting fetchAccessToken");
            return accessToken;

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @SneakyThrows
    public void deleteAccessToken(String accessToken) {

        log.info("FirebaseIntegration: Starting deleteAccessToken");

        try {

            ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(Constants.DOCUMENT_ACCESS_TOKEN).document(accessToken).delete();

            String responseTimestamp = collectionApiFuture.get().getUpdateTime().toString();

            log.info(Constants.USER_ACCESS_TOKEN_DELETED + " {}" , responseTimestamp);

            log.info("FirebaseIntegration: Exiting deleteAccessToken");

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @SneakyThrows
    public ItemDTO fetchItemByItemId(Long itemId) {

        log.info("FirebaseIntegration: Starting fetchItemByItemId");

        CollectionReference collectionReference = dbFirestore.collection(Constants.DOCUMENT_ITEM);
        Query query = collectionReference.whereEqualTo(Constants.FIELD_ITEM_ID, itemId);
        ApiFuture<QuerySnapshot> snapshotApiFuture = query.get();

        try {

            QuerySnapshot querySnapshot = snapshotApiFuture.get();

            Item item = null;
            if (!querySnapshot.isEmpty())
                item = querySnapshot.getDocuments().get(0).toObject(Item.class);

            log.info("FirebaseIntegration: Exiting fetchItemByItemId");

            return item == null ? null : modelMapper.map(item, ItemDTO.class);

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
