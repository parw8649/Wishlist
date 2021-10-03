package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
