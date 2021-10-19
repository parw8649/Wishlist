package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.exception.UnauthorizedException;
import com.wishlist.cst438project2.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/v1/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {

        log.info("AdminController: Starting getAllUsers");

        try {

            List<UserDTO> userDTOList = adminService.getAllUsers();

            log.info("UserController: Exiting saveUser");

            return userDTOList;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * This API is used for deleting user from database
     * @param username, of the user whose account is to be deleted.
     */
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestHeader String accessToken, @RequestParam String username) {

        log.info("AdminController: Starting deleteUser");

        try{
            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            if(Objects.isNull(username) || username.isEmpty())
                throw new BadRequestException();

            adminService.deleteUser(username);

            log.info("AdminController: Exiting deleteUser");

            return Constants.USER_DELETED + " " + username;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * This API is used for user registration by admin
     * @param signUpDTO
     * @return user creation timestamp
     */
    @PostMapping("/createUser")
    public String createUser(@RequestHeader String accessToken, @RequestBody SignUpDTO signUpDTO) {

        log.info("AdminController: Starting createUser");

        try {
            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            if (Objects.isNull(signUpDTO))
                throw new BadRequestException();

            String responseTimestamp = adminService.createUser(signUpDTO);

            log.info("AdminController: Exiting createUser");

            return responseTimestamp;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody SignInDTO signInDTO) {

        log.info("AdminController: Starting login");

        try {

            if (Objects.isNull(signInDTO))
                throw new BadRequestException();

            String accessToken = adminService.login(signInDTO);
            if(Objects.isNull(accessToken))
                throw new UnauthorizedException();

            log.info("AdminController: Exiting login");

            return accessToken;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * POST request to create an item in the database.
     * returns item creation timestamp
     */
    @PostMapping("/createItem")
    public String createItem(@RequestHeader String accessToken, @RequestBody ItemDTO itemDTO) {
        log.info("ItemController: Starting createItem");
        try {
            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            if (Objects.isNull(itemDTO) || (itemDTO.getName().isBlank() || Objects.isNull(itemDTO.getUserId()))) {
                throw new BadRequestException();
            } else {
                String timestamp = adminService.createItem(itemDTO);
                log.info("ItemController: exiting successful createItem");
                return timestamp;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * DELETE request to remove the item associated with a given user ID and item name
     * returns timestamp of successful deletion
     */
    @DeleteMapping("/removeItems")
    public String removeItem(@RequestHeader String accessToken, @RequestParam String itemName, @RequestParam int userId) {

        UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

        if(Objects.isNull(userTokenDTO))
            throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

        log.info("ItemController: Starting removeItem");
        log.info("ItemController: removeItem:\n    name: {}\n    userId: {}", itemName, userId);
        return adminService.removeItem(itemName, userId);
    }
}
