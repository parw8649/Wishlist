package com.wishlist.cst438project2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.controller.UserController;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for user functionalities
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objMapper;

    @Autowired
    private UserController userController;

    @Autowired
    FirebaseIntegration firebaseIntegration;

    final String USERNAME = "test-user22";
    final String PASSWORD = "user-pass22";
    final String FIRSTNAME = "test";
    final String LASTNAME = "user22";
    final String EMAIL = "testuser22@gmail.com";
    String accessToken;

    public String getAccessToken() {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername(USERNAME);
        credentials.setPassword(PASSWORD);

        ResponseDTO<UserLoginDTO> response = userController.login(credentials);
        assertThat(response.getData().getAccessToken(), notNullValue());
        return response.getData().getAccessToken();
    }

    @Test
    void saveUser_Success() {

        UserDTO userDTO = firebaseIntegration.getUser(USERNAME);
        if(Objects.nonNull(userDTO)) {
            firebaseIntegration.deleteUser(USERNAME);
        }

        SignUpDTO signUpDTO = new SignUpDTO(FIRSTNAME, LASTNAME, EMAIL, USERNAME, PASSWORD);
        String responseTimestamp = userController.saveUser(signUpDTO);
        assertThat(responseTimestamp, notNullValue());
    }

    @Test
    void userLogin_Success() {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername(USERNAME);
        credentials.setPassword(PASSWORD);

        ResponseDTO<UserLoginDTO> response = userController.login(credentials);

        String token = response.getData().getAccessToken();

        assertThat(token, notNullValue());

        this.accessToken = token;
    }

    @Test
    void updateUser_Success() {

        String newFirstName = "unitTest";
        String newLastName = "user";
        String newEmail = "unittestuser@gmail.com";

        UserDTO updateUserRequest = new UserDTO(newFirstName, newLastName, newEmail, USERNAME, null);
        UserDTO updateUserResponse = userController.updateUser(accessToken, updateUserRequest);

        assertThat(updateUserResponse, notNullValue());
        assertEquals(newFirstName, updateUserResponse.getFirstName());
        assertEquals(newLastName, updateUserResponse.getLastName());
        assertEquals(newEmail, updateUserResponse.getEmailId());
    }

    @Test
    void changePassword_Success() {

        String newPassword = "user-pass32";
        String confirmPassword = "user-pass32";

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(newPassword, confirmPassword);
        ResponseDTO<Long> responseDTO = userController.changePassword(accessToken, changePasswordDTO);

        assertThat(responseDTO.getData(), notNullValue());

        UserDTO userDTO = firebaseIntegration.getUser(USERNAME);

        assertFalse(Utils.checkPassword(PASSWORD, userDTO.getPassword()));
        assertTrue(Utils.checkPassword(newPassword, userDTO.getPassword()));
    }

    @Test
    void userLogout_Success() {

        String response = userController.logout(accessToken);

        assertEquals(Constants.USER_LOGOUT_SUCCESSFUL, response);
    }

    @Test
    void deleteMyAccount_Success() {

        String accessToken = getAccessToken();
        String updatedPassword = "user-pass32";

        DeleteUserDTO deleteUserDTO = new DeleteUserDTO(USERNAME, updatedPassword);
        String response = userController.deleteUser(accessToken, deleteUserDTO);

        assertEquals(Constants.USER_DELETED + " " + USERNAME, response);

        UserDTO userDTO = firebaseIntegration.getUser(USERNAME);

        assertTrue(Objects.isNull(userDTO));
    }
}