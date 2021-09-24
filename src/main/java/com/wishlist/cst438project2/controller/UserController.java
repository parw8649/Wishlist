package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * This API is used for user registration
     * @param user
     * @return user creation timestamp
     */
    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {

        log.info("UserController: Starting saveUser");

        if(Objects.isNull(user))
            throw new BadRequestException();

        String responseTimestamp = userService.saveUser(user);

        log.info("UserController: Exiting saveUser");

        return responseTimestamp;
    }

    /**
     * This API is used for fetching user from firebase
     * @param username (Unique key)
     * @return User object
     */
    @GetMapping("/getUser")
    public User getUser(@RequestParam String username) {

        log.info("UserController: Starting getUser");

        if(Objects.isNull(username) || username.isEmpty())
            throw new BadRequestException();

        User user = userService.getUser(username);

        log.info("UserController: Exiting getUser");

        return user;
    }
}
