package com.wishlist.cst438project2.controller;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.exception.BadRequestException;
import com.wishlist.cst438project2.exception.UnauthorizedException;
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

    @Autowired
    private TokenManager tokenManager;

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
    public UserDTO updateUser(@RequestHeader String accessToken, @RequestBody UserDTO userDTO) {

        log.info("UserController: Starting updateUser");

        try {

            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

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
     * This API is used to change user's password
     * @param changePasswordDTO
     * @return message if the password was changed successfully or returns error message
     */
    @PutMapping("/changePassword")
    public String changePassword(@RequestHeader String accessToken, @RequestBody ChangePasswordDTO changePasswordDTO) {

        log.info("UserController: Starting changePassword");

        try {

            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

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

    @PostMapping("/login")
    public String login(@RequestBody SignInDTO signInDTO) {

        log.info("UserController: Starting login");

        try {

            if (Objects.isNull(signInDTO))
                throw new BadRequestException();

            String accessToken = userService.login(signInDTO);
            if(Objects.isNull(accessToken))
                throw new UnauthorizedException();

            log.info("UserController: Exiting login");

            return accessToken;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestHeader String accessToken, @RequestBody DeleteUserDTO deleteUserDTO) {

        log.info("UserController: Starting deleteUser");

        try{

            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            userService.deleteUser(deleteUserDTO);

            log.info("UserController: Exiting deleteUser");

            return Constants.USER_DELETED + " " + deleteUserDTO.getUsername();

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader String accessToken) {

        log.info("UserController: Starting logout");

        try {
            UserTokenDTO userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            userService.logout(accessToken);

            log.info("UserController: Exiting logout");

            return Constants.USER_LOGOUT_SUCCESSFUL;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new UnauthorizedException();
        }
    }
}
