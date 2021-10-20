package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.document.Item;
import com.wishlist.cst438project2.dto.CreateItemDTO;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.dto.UserTokenDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.exception.UnauthorizedException;
import com.wishlist.cst438project2.service.ItemService;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * API endpoints for handling item entities in database
 * <p>
 * referenced:
 *     - https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/
 *     - https://www.youtube.com/watch?v=8jK9O0lwem0
 *     - https://stackoverflow.com/questions/42546141/add-location-header-to-spring-mvcs-post-response
 *     - Chaitanya's code and advice
 * @author Barbara Kondo
 * @version %I% %G%
 */
@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private TokenManager tokenManager;

    /**
     * POST request to create an item in the database.
     * returns item creation timestamp
     */
    @PostMapping
    public String createItem(@RequestHeader String accessToken, @RequestBody CreateItemDTO createItemDTO) {
        log.info("ItemController: Starting createItem");
        try {
            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);
            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            if (Objects.isNull(createItemDTO.getItemDTO()) || (createItemDTO.getItemDTO().getName().isBlank())) {
                throw new BadRequestException();
            } else {
                log.info("\n    name: " + createItemDTO.getItemDTO().getName() + "\n" + "    link: " + createItemDTO.getItemDTO().getLink() + "\n"
                        + "    description: " + createItemDTO.getItemDTO().getDescription() + "\n" + "    imgUrl: "
                        + createItemDTO.getItemDTO().getImgUrl() + "\n" + "    priority: " + createItemDTO.getItemDTO().getPriority() + "\n"
                        + "    username: " + createItemDTO.getUsername());
                String timestamp = itemService.createItem(createItemDTO.getItemDTO(), createItemDTO.getUsername());
                log.info("ItemController: exiting successful createItem");
                return timestamp;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * GET request to retrieve all items from item collection
     * returns List<ItemDTO> collection
     */
    @GetMapping
    public List<ItemDTO> getAllItems(@RequestHeader String accessToken) {
        log.info("ItemController: Starting getAllItems");
        try {

            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);
            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            List<ItemDTO> collection = itemService.getAllItems();
            log.info("ItemController: Exiting successful getAllItems");
            return collection;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * DELETE request to remove the item associated with a given user ID and item name
     * returns timestamp of successful deletion
     */
    @RequestMapping(method = RequestMethod.DELETE, params = {"item_name", "userId"}, headers = "accessToken")
    public String removeItem(@RequestHeader String accessToken, @RequestParam String item_name, @RequestParam int userId) {
        // TODO: adjust from removeItem onward to take String username --> ItemServiceImpl: convert username to userId via firebaseIntegration.getUserId(username)
        log.info("ItemController: Starting removeItem");
        log.info(String.format("ItemController: removeItem:\n    name: %s\n    userId: %s", item_name, userId));

        UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);
        if(Objects.isNull(userTokenDTO))
            throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

        String timestamp = itemService.removeItem(item_name, userId);
        return timestamp;
    }

    /**
     * PATCH request to update information of given name and userId
     * returns timestamp of successful update
     */
    @PatchMapping
    public String updateItem(@RequestHeader String accessToken, @RequestParam String item_name, @RequestBody ItemDTO updatedItemDTO) {
        log.info("ItemController: Starting updateItem");
        log.info(String.format("ItemController: updateItem:\n    name: %s\n    userId: %s", item_name, updatedItemDTO.getUserId()));

        UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);
        if(Objects.isNull(userTokenDTO))
            throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

        String timestamp = itemService.updateItem(item_name, updatedItemDTO);
        return timestamp;
    }

    /**
     * GET request to retrieve a list of items given a user's id as list identifier
     * returns list of items
     */
    @RequestMapping(method = RequestMethod.GET, params = "list", headers = "accessToken")
    // https://stackoverflow.com/a/43546809
    public List<ItemDTO> getUserItems(@RequestHeader String accessToken, @RequestParam("list") int userId) {
        log.info("ItemController: Starting getUserItems");
        log.info(String.format("ItemController: getUserItems:\n     userId: %s", userId));

        UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);
        if(Objects.isNull(userTokenDTO))
            throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

        List<ItemDTO> userItems = itemService.getUserItems(userId);
        return userItems;
    }

    /**
     * GET request to retrieve list of items matching given keywords
     * returns list of keyword relevant items
     */
    @RequestMapping(method = RequestMethod.GET, params = "search", headers = "accessToken")
    public List<ItemDTO> getSearchAllItems(@RequestHeader String accessToken, @RequestParam("search") List<String> keywords) {
        log.info("ItemController: Starting getSearchItems");
        log.info(String.format("ItemController: getSearchItems:\n     keywords: %s", keywords));

        UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);
        if(Objects.isNull(userTokenDTO))
            throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

        // TODO: add functionality to ItemService, ItemServiceImpl, and FirebaseIntegration
        List<ItemDTO> userItems = itemService.getSearchAllItems(keywords);
        return userItems;
    }

    /**
     * DELETE request to remove all items associated with given userId upon account deletion
     * returns timestamp of successful deletion
     */
    // TODO: remove mapping! I think this is ONLY supposed to be a helper for the delete user account request mapping.
    // TODO: removeItemsByUser mapping is for testing purposed only!
    @RequestMapping(method = RequestMethod.DELETE, params = "userId", headers = "accessToken")
    public String removeItemsByUser(@RequestHeader String accessToken, @RequestParam int userId) {
        log.info("ItemController: Starting removeItemsByUser");
        log.info(String.format("ItemController: removeItemsByUser:\n    userId: %s", userId));

        UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);
        if(Objects.isNull(userTokenDTO))
            throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

        String timestamp = itemService.removeItemsByUser(userId);
        log.info("ItemController: Exiting removeItemsByUser");
        return timestamp;
    }
}
