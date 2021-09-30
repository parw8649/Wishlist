package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.service.ItemService;

import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/**
 * API endpoints for handling item entities in database
 * <p>
 * referenced:
 *     - https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/
 *     - https://www.youtube.com/watch?v=8jK9O0lwem0
 *     - https://stackoverflow.com/questions/42546141/add-location-header-to-spring-mvcs-post-response
 * @author Barbara Kondo
 * @version %I% %G%
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    public ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * POST request to create an item in the database.
     * returns http response with status and URL
     */
    @PostMapping("/items")
    public ResponseEntity<String> createItem(@RequestBody ItemDTO itemDTO) throws ServerException, InterruptedException, ExecutionException {
        log.info("ItemController: Starting createItem");
        

        try {
            if (Objects.isNull(itemDTO)) {
                throw new BadRequestException();
            } else {
                String  timestamp = ItemService.createItem(itemDTO);
                log.info("ItemController: exiting successful createItem");
                return timestamp;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
        
    }
}
