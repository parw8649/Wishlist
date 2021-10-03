package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.dto.ChangePasswordDTO;
import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;
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
     * @param signUpDTO
     * @return user creation timestamp
     */
    @PostMapping("/save")
    public String saveUser(@RequestBody SignUpDTO signUpDTO) {

        log.info("UserController: Starting saveUser");

        try {

            if (Objects.isNull(signUpDTO))
                throw new BadRequestException();

            String responseTimestamp = userService.saveUser(signUpDTO);

            log.info("UserController: Exiting saveUser");

            return responseTimestamp;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * This API is used for updating user information in database
     * @param userDTO, whose details is to be updated
     * @return user creation timestamp
     */
    @PutMapping("/updateUser")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {

        log.info("UserController: Starting updateUser");

        try {

            if(Objects.isNull(userDTO))
                throw new BadRequestException();

            userDTO = userService.updateUser(userDTO);

            log.info("UserController: Exiting updateUser");

            return userDTO;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     */
    @PutMapping("/changePassword")
    public String changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {

        log.info("UserController: Starting changePassword");

        try {

            if(Objects.isNull(changePasswordDTO))
                throw new BadRequestException();

            boolean isValid = Utils.validatePassword(changePasswordDTO.getPassword(), changePasswordDTO.getConfirmPassword());

            String msg = "";
            if(isValid) {
                msg = userService.changePassword(changePasswordDTO);
            } else {
                msg = Constants.ERROR_USER_PASSWORD_MISMATCH;
            }
            log.info("UserController: Exiting changePassword");

            return msg;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
