package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;

public interface UserService {

    String saveUser(SignUpDTO signUpDTO);
    UserDTO updateUser(UserDTO userDTO);
}
