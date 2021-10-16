package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    String createItem(ItemDTO itemDTO);
    List<ItemDTO> getAllItems();
    String removeItem(String name, int userId);
    String updateItem(String name, ItemDTO updatedItem);
    List<ItemDTO> getUserItems(int userId);
    List<ItemDTO> getSearchItems(List<String> keywords);
}
