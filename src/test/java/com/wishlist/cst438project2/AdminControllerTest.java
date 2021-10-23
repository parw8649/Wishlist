package com.wishlist.cst438project2;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.controller.AdminController;
import com.wishlist.cst438project2.controller.UserController;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminControllerTest {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserController userController;

    @Autowired
    private AdminController adminController;

    @Autowired
    FirebaseIntegration firebaseIntegration;

    //Admin details
    final String ADMIN_USERNAME = "adminuser1";
    final String ADMIN_PASSWORD = "adminuserpass1";

    //User details
    final String USERNAME = "test-user22";
    final String FIRSTNAME = "test";
    final String LASTNAME = "user22";
    final String EMAIL = "testuser22@gmail.com";
    final String NEW_PASSWORD = "user-pass32";
    String PASSWORD = "user-pass22";

    @Test
    @Order(1)
    void adminLogin_Success() {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername(ADMIN_USERNAME);
        credentials.setPassword(ADMIN_PASSWORD);

        ResponseDTO<UserLoginDTO> response = adminController.login(credentials);

        String token = response.getData().getAccessToken();

        assertThat(token, notNullValue());
    }

    @Test
    @Order(1)
    void adminCreateUser_Success() {

        UserDTO userDTO = firebaseIntegration.getUser(USERNAME);
        if(Objects.nonNull(userDTO)) {
            firebaseIntegration.deleteUser(USERNAME);
        }

        String adminAccessToken = getAccessToken(ADMIN_USERNAME, ADMIN_PASSWORD);

        SignUpDTO signUpDTO = new SignUpDTO(FIRSTNAME, LASTNAME, EMAIL, USERNAME, PASSWORD);
        String responseTimestamp = adminController.createUser(adminAccessToken, signUpDTO);
        assertThat(responseTimestamp, notNullValue());
    }


    //Private Methods
    private String getAccessToken(String username, String password) {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername(username);
        credentials.setPassword(password);

        ResponseDTO<UserLoginDTO> response = adminController.login(credentials);
        assertThat(response.getData().getAccessToken(), notNullValue());
        return response.getData().getAccessToken();
    }
}
