package com.wishlist.cst438project2.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Wishlist {

    private String userId;
    private List<String> itemIds;

    public Wishlist(String userId, List<String> itemIds) {
        this.userId = userId;
        this.itemIds = itemIds;
    }
}
