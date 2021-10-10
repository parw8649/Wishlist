package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.ItemDTO;
import com.wishlist.cst438project2.dto.SignInDTO;
import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;

import java.util.List;

public interface AdminService {

    List<UserDTO> getAllUsers();
    void deleteUser(String username);

    String createUser(SignUpDTO signUpDTO);

    String login(SignInDTO signInDTO);

    String createItem(ItemDTO itemDTO);

    String removeItem(String itemName, int userId);
}
