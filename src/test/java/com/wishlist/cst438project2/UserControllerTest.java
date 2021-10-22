package com.wishlist.cst438project2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.cst438project2.controller.UserController;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        //TODO: DELETE user if it already exists in the database, inorder to run test multiple times
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
        assertThat(response.getData().getAccessToken(), notNullValue());
    }

    @Test
    void updateUser_Success() {

        String accessToken = getAccessToken();
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
}