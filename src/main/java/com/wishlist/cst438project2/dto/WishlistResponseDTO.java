package com.wishlist.cst438project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for wishlist in WishlistController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponseDTO {

    private Long userId;
    private List<ItemDTO> itemDTOList;
}
