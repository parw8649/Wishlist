package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.SignInDTO;
import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.dto.UserLoginDTO;

import java.util.List;

/**
 * Admin service interface
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

public interface AdminService {

    List<UserDTO> getAllUsers();
    void deleteUser(String username);

    String createUser(SignUpDTO signUpDTO);

    UserLoginDTO login(SignInDTO signInDTO);

    String removeItem(String itemName, Long userId);

    UserDTO updateUser(UserDTO userDTO);
}
