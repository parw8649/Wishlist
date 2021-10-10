package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;
import com.wishlist.cst438project2.service.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public String addItemsToWishlist(@RequestBody AddItemsWishlistDTO addItemsWishlistDTO) {

        log.info("WishlistController: Starting addItemsToWishlist");

        try {

            String timestamp = wishlistService.addItemsWishlist(addItemsWishlistDTO);

            log.info("WishlistController: Exiting addItemsToWishlist");

            return timestamp;

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
