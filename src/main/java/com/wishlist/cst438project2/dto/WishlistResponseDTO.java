package com.wishlist.cst438project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponseDTO {

    private String userId;
    private List<ItemDTO> itemDTOList;
}
