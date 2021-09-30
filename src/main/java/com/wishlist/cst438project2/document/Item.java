package com.wishlist.cst438project2.document;

import com.google.cloud.firestore.annotation.DocumentId;
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
    @DocumentId
    private String name;
    private String link;
    private String description;
    private String imgUrl;

    /*
    public Item(String name, String link, String description, String imgUrl) {
        this(name, link, description);
        this.imgUrl = imgUrl;
    }

    public Item(String name, String link, String description) {
        this(name, link);
        this.description = description;
    }

    public Item(String name, String link) {
        this(name);
        this.link = link;
    }
    */

    /**
     * Creates an Item with a given String name. Item objects must have an assigned name.
     * @param name is the name or title of the item
     */
     /*
    public Item(String name) {
        this.name = name;
    }
    */

    public ItemDTO fetchItemDTO() {
        return new ItemDTO(name, link, description, imgUrl);
    }

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
    
}
