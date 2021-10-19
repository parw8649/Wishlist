package com.wishlist.cst438project2.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.dto.ItemDTO;
import lombok.Data;

/**
 * represents an item to be referenced by Wishlist Class.
 * referenced constructor chaining: https://www.geeksforgeeks.org/constructor-chaining-java-examples/
 * @author Barbara Kondo
 * @version %I% %G%
 */

@Data
public class Item {
    private String name;
    private String link;
    private String description;
    private String imgUrl;
    private int userId;
    private String priority;

    /**
     * returns an Item Data Transfer Object with parameters matching the current Item obj
     */
    public ItemDTO fetchItemDTO() {
        return new ItemDTO(name, link, description, imgUrl, userId, priority);
    }

    /*
    public String itemToPostString() {
        string itemPost = "";
        itemPost += String.format("item_name=%s", name);
        if (!link.equals(null)) {
            itemPost += String.format("&url=%s", link);
        }
        if (!description.equals(null)) {
            itemPost += String.format("&description=%s", description);
        }
        if (!imgUrl.equals(null)) {
            itemPost += String.format("&img=%s", imgUrl);
        }
        return itemPost;
    }
    */

}