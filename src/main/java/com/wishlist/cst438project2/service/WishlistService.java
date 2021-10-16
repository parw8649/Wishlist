package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.document.Wishlist;
import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;

public interface WishlistService {

    Wishlist addItemsWishlist(AddItemsWishlistDTO addItemsWishlistDTO);
}
