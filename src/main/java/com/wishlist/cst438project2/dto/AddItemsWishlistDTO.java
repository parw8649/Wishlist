package com.wishlist.cst438project2.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddItemsWishlistDTO {

    @NotEmpty(message = "UserId must not be null or empty")
    private String userId;

    @NotNull
    private List<String> itemIds;
}
