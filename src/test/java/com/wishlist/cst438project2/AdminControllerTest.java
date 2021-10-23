package com.wishlist.cst438project2;

import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.TokenManager;
import com.wishlist.cst438project2.controller.AdminController;
import com.wishlist.cst438project2.controller.ItemController;
import com.wishlist.cst438project2.controller.UserController;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private ItemController itemController;

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

    //Item details
    final String INITIAL_ITEM_NAME = "test item name";

    @Test
    @Order(1)
    void adminLogin_Success() {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername(ADMIN_USERNAME);
        credentials.setPassword(ADMIN_PASSWORD);

        ResponseDTO<UserLoginDTO> response = adminController.login(credentials);

        String token = response.getData().getAccessToken();

        assertThat(token, notNullValue());

        userController.logout(token);
    }

    @Test
    @Order(2)
    void adminCreateUser_Success() {

        UserDTO userDTO = firebaseIntegration.getUser(USERNAME);
        if(Objects.nonNull(userDTO)) {
            firebaseIntegration.deleteUser(USERNAME);
        }

        String adminAccessToken = getAdminAccessToken();
        SignUpDTO signUpDTO = new SignUpDTO(FIRSTNAME, LASTNAME, EMAIL, USERNAME, PASSWORD);
        String responseTimestamp = adminController.createUser(adminAccessToken, signUpDTO);
        assertThat(responseTimestamp, notNullValue());

        userController.logout(adminAccessToken);
    }

    @Test
    @Order(3)
    void updateUserWithoutPasswordChange_Success() {

        String newFirstName = "unitTest";
        String newLastName = "user";
        String newEmail = "unittestuser@gmail.com";

        String adminAccessToken = getAdminAccessToken();
        UserDTO updateUserRequest = new UserDTO(newFirstName, newLastName, newEmail, USERNAME, null);
        UserDTO updateUserResponse = adminController.updateUser(adminAccessToken, updateUserRequest);

        assertThat(updateUserResponse, notNullValue());
        assertEquals(newFirstName, updateUserResponse.getFirstName());
        assertEquals(newLastName, updateUserResponse.getLastName());
        assertEquals(newEmail, updateUserResponse.getEmailId());

        userController.logout(adminAccessToken);
    }

    @Test
    @Order(4)
    void updateUserWithPasswordChange_Success() {

        String newFirstName = "unitTest";
        String newLastName = "user";
        String newEmail = "unittestuser@gmail.com";

        String adminAccessToken = getAdminAccessToken();
        UserDTO updateUserRequest = new UserDTO(newFirstName, newLastName, newEmail, USERNAME, NEW_PASSWORD);
        UserDTO updateUserResponse = adminController.updateUser(adminAccessToken, updateUserRequest);

        assertThat(updateUserResponse, notNullValue());
        assertEquals(newFirstName, updateUserResponse.getFirstName());
        assertEquals(newLastName, updateUserResponse.getLastName());
        assertEquals(newEmail, updateUserResponse.getEmailId());

        //Checking user login with new password
        String userAccessToken = getUserAccessToken(USERNAME, NEW_PASSWORD);

        UserTokenDTO userTokenDTO = tokenManager.getUser(userAccessToken);
        assertThat(updateUserResponse, notNullValue());
        assertEquals(updateUserResponse.getUserId(), userTokenDTO.getUserId());
        assertEquals(updateUserResponse.getRole(), userTokenDTO.getRole());

        userController.logout(adminAccessToken);
        userController.logout(userAccessToken);
    }

    @Test
    @Order(5)
    void adminCreateItem_Success() {

        String userAccessToken = getUserAccessToken(USERNAME, NEW_PASSWORD);

        /*List<ItemDTO> userItemsResponse = itemController.getUserItems(userAccessToken, USERNAME);
        if (userItemsResponse.size() > 0) {
            String clearUserItemsResponse = itemController.removeItemsByUser(userAccessToken, USERNAME);
            assertEquals(clearUserItemsResponse.substring(0, 5), "2021-");
        }*/

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(INITIAL_ITEM_NAME);

        String adminAccessToken = getAdminAccessToken();
        String createResponse = adminController.createItem(adminAccessToken, itemDTO, USERNAME);
        assertEquals(createResponse.substring(0,5), "2021-");

        List<ItemDTO> userItemsResponse = itemController.getUserItems(userAccessToken, USERNAME);
        assert(userItemsResponse.size() == 1);

        userController.logout(adminAccessToken);
        userController.logout(userAccessToken);
    }

    @Test
    @Order(6)
    void adminRemoveItem_Success() {

        String userAccessToken = getUserAccessToken(USERNAME, NEW_PASSWORD);

        List<ItemDTO> userItemsResponse = itemController.getUserItems(userAccessToken, USERNAME);
        assert(userItemsResponse.size() == 1);

        Long userId = firebaseIntegration.getUserId(USERNAME);

        String adminAccessToken = getAdminAccessToken();
        String removeResponse = adminController.removeItem(adminAccessToken, INITIAL_ITEM_NAME, userId);
        System.out.println(removeResponse);
        assertEquals(removeResponse.substring(0,5), "2021-");

        userItemsResponse = itemController.getUserItems(userAccessToken, USERNAME);
        assert(userItemsResponse.size() == 0);

        userController.logout(adminAccessToken);
        userController.logout(userAccessToken);
    }

    @Test
    @Order(7)
    void adminLogout_Success() {

        String response = userController.logout(getAdminAccessToken());

        assertEquals(Constants.USER_LOGOUT_SUCCESSFUL, response);
    }

    @Test
    @Order(8)
    void deleteUserAccount_Success() {

        String adminAccessToken = getAdminAccessToken();

        String response = adminController.deleteUser(adminAccessToken, USERNAME);

        assertEquals(Constants.USER_DELETED + " " + USERNAME, response);

        UserDTO userDTO = firebaseIntegration.getUser(USERNAME);

        assertTrue(Objects.isNull(userDTO));

        userController.logout(adminAccessToken);
    }

    //Private Methods
    private String getAdminAccessToken() {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername(ADMIN_USERNAME);
        credentials.setPassword(ADMIN_PASSWORD);

        ResponseDTO<UserLoginDTO> response = adminController.login(credentials);
        assertThat(response.getData().getAccessToken(), notNullValue());
        return response.getData().getAccessToken();
    }

    private String getUserAccessToken(String username, String password) {
        SignInDTO credentials = new SignInDTO();
        credentials.setUsername(username);
        credentials.setPassword(password);

        ResponseDTO<UserLoginDTO> response = userController.login(credentials);
        assertThat(response.getData().getAccessToken(), notNullValue());
        return response.getData().getAccessToken();
    }
}
