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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * API endpoints for handling the user functions
 *
 * references:
 *     - https://spring.io/projects/spring-boot
 *     - https://stackoverflow.com/questions/29479814/spring-mvc-or-spring-boot
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@CrossOrigin
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
     * @param signUpDTO contains params reuired for user sign up process
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

            return Objects.nonNull(responseTimestamp) && !responseTimestamp.isEmpty()
                    ? Constants.USER_CREATED : Constants.ERROR_UNABLE_TO_CREATE_USER;

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
     * @param changePasswordDTO contains params reuired for changing user password
     * @return message if the password was changed successfully or returns error message
     */
    @PutMapping("/changePassword")
    public ResponseDTO<Long> changePassword(@RequestHeader String accessToken, @RequestBody ChangePasswordDTO changePasswordDTO) {

        log.info("UserController: Starting changePassword");

        ResponseDTO<Long> responseDTO = new ResponseDTO<>();
        String msg;
        UserTokenDTO userTokenDTO;
        int httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        try {

            userTokenDTO = tokenManager.getUser(accessToken);

            if(Objects.isNull(userTokenDTO))
                throw new UnauthorizedException(Constants.ERROR_INVALID_TOKEN);

            if(Objects.isNull(changePasswordDTO))
                throw new BadRequestException();

            boolean isValid = Utils.validatePassword(changePasswordDTO.getNewPassword(), changePasswordDTO.getConfirmPassword());

            if(isValid) {
                msg = userService.changePassword(userTokenDTO.getUsername(), changePasswordDTO);
                httpStatusCode = HttpStatus.OK.value();
            } else {
                msg = Constants.ERROR_USER_PASSWORD_MISMATCH;
            }

            responseDTO.setStatus(httpStatusCode);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            if(ex instanceof UnauthorizedException)
                httpStatusCode = HttpStatus.UNAUTHORIZED.value();
            else if (ex instanceof BadRequestException)
                httpStatusCode = HttpStatus.BAD_REQUEST.value();

            responseDTO.setStatus(httpStatusCode);
            throw ex;
        }

        log.info("UserController: Exiting changePassword");

        responseDTO.setData(userTokenDTO.getUserId());
        responseDTO.setMessage(msg);
        return responseDTO;
    }

    @PostMapping("/login")
    public ResponseDTO<UserLoginDTO> login(@RequestBody SignInDTO signInDTO) {

        log.info("UserController: Starting login");

        ResponseDTO<UserLoginDTO> responseDTO = new ResponseDTO<>();
        UserLoginDTO userLoginDTO = null;
        String message = HttpStatus.UNAUTHORIZED.toString();
        int httpStatusCode;
        try {

            if (Objects.isNull(signInDTO))
                throw new BadRequestException();

            userLoginDTO = userService.login(signInDTO);
            if(Objects.isNull(userLoginDTO))
                throw new UnauthorizedException();

            httpStatusCode = HttpStatus.OK.value();
            message = Constants.USER_LOGIN_SUCCESSFUL;
            log.info("UserController: Exiting login");

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            httpStatusCode = HttpStatus.UNAUTHORIZED.value();
        }

        responseDTO.setStatus(httpStatusCode);
        responseDTO.setData(userLoginDTO);
        responseDTO.setMessage(message);
        return responseDTO;
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
