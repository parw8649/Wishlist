package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.*;

import java.util.List;

public interface AdminService {

    List<UserDTO> getAllUsers();
    void deleteUser(String username);

    String createUser(SignUpDTO signUpDTO);

    UserLoginDTO login(SignInDTO signInDTO);

    String createItem(ItemDTO itemDTO);

    String removeItem(String itemName, int userId);
}
