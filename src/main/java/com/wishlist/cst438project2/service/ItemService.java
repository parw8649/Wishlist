package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.ItemDTO;
import java.util.List;

/**
 * Service methods to be used with API endpoints in ItemController.java
 * <p>
 * serves as abstraction layer
 * @author Barbara Kondo
 * @version %I% %G%
 */
public interface ItemService {
    /**
     * database record creation route for item associated with user.
     * <p>
     * returns timestamp of successful record creation.
     */
    String createItem(ItemDTO itemDTO, String username);

    /**
     * retrieve all documents from item collection
     * returns a list of all created items
     */
    List<ItemDTO> getAllItems();

    /**
     * remove the item associated with a given user and item name
     * returns timestamp of deletion
     */
    String removeItem(String name, String username);

    /**
     * update the item associated with a userId matching the updatedItemDTO and old item name
     * returns timestamp of successful update
     */
    String updateItem(String name, ItemDTO updatedItem);

    /**
     * returns a list of items associated with a given username
     */
    List<ItemDTO> getUserItems(String username);

    /**
     * returns a list of items based on search keywords
     */
    List<ItemDTO> getSearchAllItems(List<String> keywords);

    /**
     * remove every item associated with a given user
     * returns timestamp of successful deletion
     */
    String removeItemsByUser(String username);
}
