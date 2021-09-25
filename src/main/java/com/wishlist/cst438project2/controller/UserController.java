package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * This API is used for user registration
     * @param userDTO
     * @return user creation timestamp
     */
    @PostMapping("/save")
    public String saveUser(@RequestBody UserDTO userDTO) {

        log.info("UserController: Starting saveUser");

        try {

            if (Objects.isNull(userDTO))
                throw new BadRequestException();

            String responseTimestamp = userService.saveUser(userDTO);

            log.info("UserController: Exiting saveUser");

            return responseTimestamp;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
