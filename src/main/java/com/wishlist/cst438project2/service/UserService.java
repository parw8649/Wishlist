package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.ChangePasswordDTO;
import com.wishlist.cst438project2.dto.SignInDTO;
import com.wishlist.cst438project2.dto.SignUpDTO;
import com.wishlist.cst438project2.dto.UserDTO;

public interface UserService {

    String saveUser(SignUpDTO signUpDTO);
    UserDTO updateUser(UserDTO userDTO);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    String login(SignInDTO signInDTO);
}
