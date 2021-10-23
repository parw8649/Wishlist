package com.wishlist.cst438project2.common.extras;

import com.wishlist.cst438project2.common.extras.Wishlist;
import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;
import com.wishlist.cst438project2.dto.WishlistResponseDTO;

/**
 * Wishlist service interface
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

public interface WishlistService {

    Wishlist addItemsWishlist(AddItemsWishlistDTO addItemsWishlistDTO);

    WishlistResponseDTO getWishlistByUser(Long userId);
}
