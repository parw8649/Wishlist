package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.UserDTO;

import java.util.List;

public interface AdminService {

    List<UserDTO> getAllUsers();
    void deleteUser(String username);
}
