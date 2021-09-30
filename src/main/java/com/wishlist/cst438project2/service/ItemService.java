package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.itemDTO;

@Service
public interface ItemService {
    String createItem(ItemDTO itemDTO);
}
