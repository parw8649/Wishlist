package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.document.Wishlist;
import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;
import com.wishlist.cst438project2.dto.WishlistResponseDTO;

public interface WishlistService {

    Wishlist addItemsWishlist(AddItemsWishlistDTO addItemsWishlistDTO);

    WishlistResponseDTO getWishlistByUser(String userId);
}
