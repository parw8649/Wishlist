package com.wishlist.cst438project2.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for createItem endpoint in ItemController.java
 * @author Barbara Kondo
 * @version %I% %G%
 */
@Data
@Slf4j
@NoArgsConstructor
public class CreateItemDTO {
    private ItemDTO itemDTO;
    private String username;

    public CreateItemDTO(ItemDTO itemDTO, String username) {
        this.itemDTO = itemDTO;
        this.username = username;
    }

    public void logCreateItemDTO() {
        log.info("\n    name: " + this.getItemDTO().getName() + "\n" + "    link: " + this.getItemDTO().getLink() + "\n"
                + "    description: " + this.getItemDTO().getDescription() + "\n" + "    imgUrl: "
                + this.getItemDTO().getImgUrl() + "\n" + "    priority: " + this.getItemDTO().getPriority() + "\n"
                + "    username: " + this.getUsername());
    }
}
