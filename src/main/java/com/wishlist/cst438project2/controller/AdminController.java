package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

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
    public String deleteUser(@RequestParam String username) {

        log.info("AdminController: Starting deleteUser");

        try{

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
    public String createUser(@RequestBody SignUpDTO signUpDTO) {

        log.info("AdminController: Starting createUser");

        try {

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
}
