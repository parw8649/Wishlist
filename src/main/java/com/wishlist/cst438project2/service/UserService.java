package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.document.User;

public interface UserService {

    String saveUser(User user);

    User getUser(String username);
}
