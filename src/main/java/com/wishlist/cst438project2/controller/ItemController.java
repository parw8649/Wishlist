package com.wishlist.cst438project2.controller;

import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.concurrent.ExecutionException;

import com.wishlist.cst438project2.document.Item;
import com.wishlist.cst438project2.service.ItemService;

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
    public ResponseEntity<String> createItem(@RequestBody Item newitem) throws InterruptedException, ExecutionException {
        Item  item = ItemService.createItem(newitem);
        if (item == null) {
            throw new ServerException("Remote exception: Item creation failed.");
        } else {
            String location = ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/items")
                                .buildAndExpand()
                                .toUriString();
            //TODO: Is this correct for a POST response? 
            return ResponseEntity.status(HttpStatus.SC_CREATED).header(HttpHeaders.LOCATION, location).build();
        }
    }
}
