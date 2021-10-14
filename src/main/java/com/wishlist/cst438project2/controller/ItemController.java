package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.document.Item;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
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

    /**
     * POST request to create an item in the database.
     * returns item creation timestamp
     */
    @PostMapping
    public String createItem(@RequestBody ItemDTO itemDTO) {
        log.info("ItemController: Starting createItem");
        try {
            if (Objects.isNull(itemDTO) || (itemDTO.getName().isBlank() || Objects.isNull(itemDTO.getUserId()))) {
                throw new BadRequestException();
            } else {
                log.info("\n    name: " + itemDTO.getName() + "\n" + "    link: " + itemDTO.getLink() + "\n"
                        + "    description: " + itemDTO.getDescription() + "\n" + "    imgUrl: "
                        + itemDTO.getImgUrl() + "\n" + "    userId: " + itemDTO.getUserId() + "\n"
                        + "    priority: " + itemDTO.getPriority());
                String timestamp = itemService.createItem(itemDTO);
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
    public List<ItemDTO> getAllItems() {
        log.info("ItemController: Starting getAllItems");
        try {
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
    @DeleteMapping
    public String removeItem(@RequestParam String item_name, @RequestParam int userId) {
        log.info("ItemController: Starting removeItem");
        log.info(String.format("ItemController: removeItem:\n    name: %s\n    userId: %s", item_name, userId));
        // TODO: add item delete confirmation message --> I think that's front end
        String timestamp = itemService.removeItem(item_name, userId);
        return timestamp;
    }

    /**
     * PATCH request to update information of given name and userId
     * returns timestamp of successful update
     */
    @PatchMapping
    public String updateItem(@RequestParam String item_name, @RequestBody ItemDTO updatedItemDTO) {
        log.info("ItemController: Starting updateItem");
        log.info(String.format("ItemController: updateItem:\n    name: %s\n    userId: %s", item_name, updatedItemDTO.getUserId()));
        String timestamp = itemService.updateItem(item_name, updatedItemDTO);
        return timestamp;
    }

    /**
     * GET request to retrieve a list of items given a user's id as list identifier
     * returns list of items
     */
    @RequestMapping(method = RequestMethod.GET, params = "list")
    // https://stackoverflow.com/a/43546809
    public List<ItemDTO> getUserItems(@RequestParam("list") int userId) {
        log.info("ItemController: Starting getUserItems");
        log.info(String.format("ItemController: getUserItems:\n     userId: %s", userId));
        List<ItemDTO> userItems = itemService.getUserItems(userId);
        return userItems;
    }
}
