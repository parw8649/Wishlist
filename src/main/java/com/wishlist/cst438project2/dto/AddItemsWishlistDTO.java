package com.wishlist.cst438project2.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddItemsWishlistDTO {

    private Long userId;

    @NotNull
    private List<Long> itemIds;
}
