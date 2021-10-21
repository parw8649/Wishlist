package com.wishlist.cst438project2.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.document.Item;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import com.wishlist.cst438project2.service.ItemService;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service method implementations to be used with API endpoints in ItemController.java.
 *
 * @author Barbara Kondo
 * @version %I% %G%
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    /**
     * database record creation route for item.
     * <p>
     * returns timestamp of successful record creation.
     */
    @SneakyThrows
    @Override
    public String createItem(ItemDTO itemDTO, String username) {
        log.info("ItemServiceImpl: starting createItem");
        long userId = firebaseIntegration.getUserId(username);

        // check for existence of item by name and userId
        Item item = fetchItem(itemDTO.getName(), userId);
        // if item exists, don't add an identical item? throw err
        if (Objects.nonNull(item)) {
            throw new BadRequestException(Constants.ERROR_ITEM_ALREADY_EXISTS.replace(Constants.KEY_ITEM_NAME, itemDTO.getName()));
        } else {
            // If item does not exist, add the item
            item = modelMapper.map(itemDTO, Item.class);
            item.setUserId(userId);
            item.logItem();
        }

        /*
         * let item documents have a randomly assigned docId, otherwise multiple users can't have items with the same name. . .
         * I had issues before because I was setting the docId to the item name --> firebase overrode the name field in favor of docId
         */
        ApiFuture<WriteResult> collectionsApiFuture = firebaseIntegration.dbFirestore.collection(Constants.DOCUMENT_ITEM).document().set(item);
        String timestamp = collectionsApiFuture.get().getUpdateTime().toString();

        log.info("ItemServiceImpl: createItem: timestamp: {}", timestamp);
        log.info("ItemServiceImpl: exiting createItem");
        return timestamp;
    }

    /**
     * retrieve all documents from item collection
     * returns a list of all created items
     */
    @SneakyThrows
    @Override
    public List<ItemDTO> getAllItems() {
        log.info("ItemServiceImpl: starting getAllItems");
        List<ItemDTO> collection = firebaseIntegration.getAllItems();

        log.info("ItemServiceImpl: exiting getAllItems");
        return collection;
    }

    /**
     * remove the item associated with a given user and item name
     * returns timestamp of deletion
     */
    @SneakyThrows
    @Override
    public String removeItem(String name, String username) {
        log.info("ItemServiceImpl: Starting removeItem");
        long userId = firebaseIntegration.getUserId(username);
        String docId = firebaseIntegration.getItemDocId(name, userId);

        String timestamp = firebaseIntegration.removeItem(docId);
        log.info("ItemServiceImpl: exiting removeItem");
        return timestamp;
    }

    /**
     * update the item associated with a userId matching the updatedItemDTO and old item name
     * returns timestamp of successful update
     */
    @SneakyThrows
    @Override
    public String updateItem(String name, ItemDTO updatedItemDTO) {
        log.info("ItemServiceImpl: Starting updateItem");
        String docId = firebaseIntegration.getItemDocId(name, updatedItemDTO.getUserId());

        String timestamp = firebaseIntegration.updateItem(docId, updatedItemDTO);
        log.info("ItemServiceImpl: Exiting updateItem");
        return timestamp;
    }

    /**
     * returns a list of items associated with a given username
     */
    @SneakyThrows
    @Override
    public List<ItemDTO> getUserItems(String username) {
        log.info("ItemServiceImpl: Starting updateItem");
        List<ItemDTO> userItems = firebaseIntegration.getUserItems(username);
        log.info("ItemServiceImpl: Exiting updateItem");
        return userItems;
    }

    /**
     * returns a list of items based on search keywords
     */
    @SneakyThrows
    @Override
    public List<ItemDTO> getSearchAllItems(List<String> keywords) {
        log.info("ItemServiceImpl: Starting getSearchItems");
        List<ItemDTO> searchItems = firebaseIntegration.getSearchAllItems(keywords);
        log.info("ItemServiceImpl: Exiting getSearchItems");
        return searchItems;
    }

    /**
     * remove every item associated with a given user
     * returns timestamp of successful deletion
     */
    @SneakyThrows
    @Override
    public String removeItemsByUser(String username) {
        log.info("ItemServiceImpl: Starting removeItemsByUser");
        String timestamp = firebaseIntegration.removeItemsByUser(username);
        log.info("ItemServiceImpl: Exiting removeItemsByUser");
        return timestamp;
    }

    /**
     * utility method
     * returns the item found in database by given name
     */
    private Item fetchItem(String name, long userId) {
        ItemDTO dbItemDTO = firebaseIntegration.getItem(name, userId);

        if(Objects.isNull(dbItemDTO)) {
//            throw new BadRequestException(Constants.ERROR_ITEM_DOES_NOT_EXISTS.replace(Constants.KEY_ITEM_NAME, name));
            Item item = null;
            return item;
        }

        return modelMapper.map(dbItemDTO, Item.class);
    }
}
