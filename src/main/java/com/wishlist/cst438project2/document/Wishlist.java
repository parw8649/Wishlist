package com.wishlist.cst438project2.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * wishlist
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */
@Data
@NoArgsConstructor
public class Wishlist {

    private Long userId;
    private List<Long> itemIds;

    public Wishlist(Long userId, List<Long> itemIds) {
        this.userId = userId;
        this.itemIds = itemIds;
    }
}
