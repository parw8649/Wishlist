package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.UserDTO;

public interface UserService {

    String saveUser(UserDTO userDTO);
}
