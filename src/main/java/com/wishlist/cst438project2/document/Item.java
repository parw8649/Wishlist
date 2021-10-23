package com.wishlist.cst438project2.document;

import com.wishlist.cst438project2.dto.ItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * represents an item to be associated with a valid user.
 * @author Barbara Kondo
 * @version %I% %G%
 */
@Slf4j
@Data
@NoArgsConstructor
public class Item {
//    private Long itemId;
    private String name;
    private String link;
    private String description;
    private String imgUrl;
    private long userId;
    private String priority;

    /**
     * returns an Item Data Transfer Object with parameters matching the current Item obj
     */
    public ItemDTO fetchItemDTO() {
        return new ItemDTO(name, link, description, imgUrl, userId, priority);
    }

    public void logItem() {
        log.info("\n    name: " + this.getName() + "\n" + "    link: " + this.getLink() + "\n"
                + "    description: " + this.getDescription() + "\n" + "    imgUrl: "
                + this.getImgUrl() + "\n" + "    userId: " + this.getUserId() + "\n"
                + "    priority: " + this.getPriority());
    }
}