package com.wishlist.cst438project2.dto;

import com.wishlist.cst438project2.dto.ItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateItemDTO {
    private ItemDTO itemDTO;
    private String username;

    public CreateItemDTO(ItemDTO itemDTO, String username) {
        this.itemDTO = itemDTO;
        this.username = username;
    }
}
