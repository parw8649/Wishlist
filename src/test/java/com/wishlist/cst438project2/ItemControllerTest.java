package com.wishlist.cst438project2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.cst438project2.controller.ItemController;
import com.wishlist.cst438project2.controller.UserController;
import com.wishlist.cst438project2.dto.*;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest({ItemController.class, UserController.class})
public class ItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objMapper;

    @Autowired
    private ItemController itemController;

    @Autowired
    private UserController userController;

    @Autowired
    FirebaseIntegration firebaseIntegration;

    /**
     * only one valid user is used for these tests
     */
    final String USERNAME = "unit-User";

    final String PASSWORD = "unit-Pass";

    final String INITIAL_ITEM_NAME = "test name";

    final String UPDATE_ITEM_NAME = "updated name";

    /**
     * utility function to retrieve accessToken from successful login
     */
    public String getAccessToken() {
        UserController userControl = new UserController();
		SignInDTO credentials = new SignInDTO();
		credentials.setUsername(USERNAME);
		credentials.setPassword(PASSWORD);
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .contentType(this.objMapper.writeValueAsString(credentials));
//
//		mockMvc.perform(mockRequest)
//                .andExpect(status().isOk());

        ResponseDTO<UserLoginDTO> response = userController.login(credentials);
        assertThat(response.getData().getAccessToken(), notNullValue());
        return response.getData().getAccessToken();
    }

    /**
     * utility function to add an item from database,
     * uses same creation code as createItem_Success()
     * @return String timestamp of successful creation
     */
    public String addItemToDb(String accessToken, String item_name) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(item_name);

        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemDTO(itemDTO);
        createItemDTO.setUsername(USERNAME);
        System.out.println(createItemDTO);

        return itemController.createItem(accessToken, createItemDTO);
    }

    @Test
    public void getAllItems_Success() {
        String token = getAccessToken();
        List<ItemDTO> userItemsResponse = itemController.getUserItems(token, USERNAME);
        if (userItemsResponse.size() > 0) {
            String clearUserItemsResponse = itemController.removeItemsByUser(token, USERNAME);
            assertEquals(clearUserItemsResponse.substring(0, 5), "2021-");
        }

        List<ItemDTO> allItemsResponse = itemController.getAllItems(token);
        assert(allItemsResponse.size() > 0);
    }

    @Test
    public void getUserItems_Success() {
        String token = getAccessToken();
        List<ItemDTO> userItemsResponse = itemController.getUserItems(token, USERNAME);
        if (userItemsResponse.size() > 0) {
            String clearUserItemsResponse = itemController.removeItemsByUser(token, USERNAME);
            assertEquals(clearUserItemsResponse.substring(0, 5), "2021-");
        }

        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 0);
    }

    @Test
    public void createItem_Success() {
        String token = getAccessToken();
        List<ItemDTO> userItemsResponse = itemController.getUserItems(token, USERNAME);
        if (userItemsResponse.size() > 0) {
            String clearUserItemsResponse = itemController.removeItemsByUser(token, USERNAME);
            assertEquals(clearUserItemsResponse.substring(0, 5), "2021-");
        }

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(INITIAL_ITEM_NAME);

        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemDTO(itemDTO);
        createItemDTO.setUsername(USERNAME);
        System.out.println(createItemDTO);

        String createResponse = itemController.createItem(token, createItemDTO);
        System.out.println(createResponse);
        assertEquals(createResponse.substring(0,5), "2021-");

        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 1);

        String removeResponse = itemController.removeItem(token, itemDTO.getName(), USERNAME);
        assertEquals(removeResponse.substring(0,5), "2021-");

        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 0);
    }

    @Test
    public void updateItem_Success() {
        String token = getAccessToken();
        List<ItemDTO> userItemsResponse = itemController.getUserItems(token, USERNAME);
        if (userItemsResponse.size() > 0) {
            String clearUserItemsResponse = itemController.removeItemsByUser(token, USERNAME);
            assertEquals(clearUserItemsResponse.substring(0, 5), "2021-");
        }
        addItemToDb(token, INITIAL_ITEM_NAME);
        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 1);

        ItemDTO updatedItemDTO = new ItemDTO();
        updatedItemDTO.setName(UPDATE_ITEM_NAME);
        updatedItemDTO.setUserId(firebaseIntegration.getUserId(USERNAME));

        String updateResponse = itemController.updateItem(token, INITIAL_ITEM_NAME, updatedItemDTO);
        System.out.println(updateResponse);
        assertEquals(updateResponse.substring(0,5), "2021-");

        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 1);

        String removeResponse = itemController.removeItem(token, updatedItemDTO.getName(), USERNAME);
        assertEquals(removeResponse.substring(0,5), "2021-");

        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 0);
    }

    @Test
    public void removeItem_Success() {
        String token = getAccessToken();
        List<ItemDTO> userItemsResponse = itemController.getUserItems(token, USERNAME);
        if (userItemsResponse.size() > 0) {
            String clearUserItemsResponse = itemController.removeItemsByUser(token, USERNAME);
            assertEquals(clearUserItemsResponse.substring(0, 5), "2021-");
        }
        addItemToDb(token, INITIAL_ITEM_NAME);
        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 1);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(INITIAL_ITEM_NAME);
        itemDTO.setUserId(firebaseIntegration.getUserId(USERNAME));

        String removeResponse = itemController.removeItem(token, INITIAL_ITEM_NAME, USERNAME);
        System.out.println(removeResponse);
        assertEquals(removeResponse.substring(0,5), "2021-");

        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 0);
    }

    @Test
    public void removeAllItemsByUser_Success() {
        String token = getAccessToken();
        addItemToDb(token, "item 1");
        addItemToDb(token, "item 2");
        addItemToDb(token, "item 3");
        List<ItemDTO> userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() >= 3);

        String removeAllResponse = itemController.removeItemsByUser(token, USERNAME);

        userItemsResponse = itemController.getUserItems(token, USERNAME);
        assert(userItemsResponse.size() == 0);
    }
}
