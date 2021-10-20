package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.ItemDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ItemService {
    String createItem(ItemDTO itemDTO, String username);
    List<ItemDTO> getAllItems();
    String removeItem(String name, int userId);
    String updateItem(String name, ItemDTO updatedItem);
    List<ItemDTO> getUserItems(int userId);
    List<ItemDTO> getSearchAllItems(List<String> keywords);
    String removeItemsByUser(int username);
}
