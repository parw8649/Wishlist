package com.wishlist.cst438project2.common.extras;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.dto.AddItemsWishlistDTO;
import com.wishlist.cst438project2.dto.UserTokenDTO;
import com.wishlist.cst438project2.dto.WishlistResponseDTO;
import com.wishlist.cst438project2.exception.ExternalServerException;
import com.wishlist.cst438project2.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
/**
 * Wishlist Controller is used for adding user items into the wishlist,
 * and fetching wishlist data specific to user
 *
 * references:
 *     - https://spring.io/projects/spring-boot
 *     - https://stackoverflow.com/questions/29479814/spring-mvc-or-spring-boot
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@CrossOrigin
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

    @GetMapping("/view")
    public WishlistResponseDTO getWishlistByUser(@RequestHeader String accessToken) {

        log.info("WishlistController: Starting getWishlistByUser");

        try {
            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            WishlistResponseDTO wishlistResponseDTO = wishlistService.getWishlistByUser(userTokenDTO.getUserId());

            if(Objects.isNull(wishlistResponseDTO))
                throw new ExternalServerException(Constants.ERROR_UNABLE_TO_FETCH_WISHLIST);

            log.info("WishlistController: Exiting getWishlistByUser");

            return wishlistResponseDTO;

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
