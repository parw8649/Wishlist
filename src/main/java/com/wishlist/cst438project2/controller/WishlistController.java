package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.document.Wishlist;
import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;
import com.wishlist.cst438project2.dto.UserTokenDTO;
import com.wishlist.cst438project2.exception.ExternalServerException;
import com.wishlist.cst438project2.exception.UnauthorizedException;
import com.wishlist.cst438project2.service.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/add")
    public Wishlist addItemsToWishlist(@RequestHeader String accessToken, @RequestBody AddItemsWishlistDTO addItemsWishlistDTO) {

        log.info("WishlistController: Starting addItemsToWishlist");

        try {
            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            addItemsWishlistDTO.setUserId(userTokenDTO.getUserId());

            Wishlist wishlist = wishlistService.addItemsWishlist(addItemsWishlistDTO);

            if(Objects.isNull(wishlist))
                throw new ExternalServerException(Constants.ERROR_UNABLE_TO_ADD_ITEM_TO_WISHLIST);

            log.info("WishlistController: Exiting addItemsToWishlist");

            return wishlist;

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
