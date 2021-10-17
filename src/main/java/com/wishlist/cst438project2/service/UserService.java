package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.*;

public interface UserService {

    String saveUser(SignUpDTO signUpDTO);
    UserDTO updateUser(UserDTO userDTO);

    String changePassword(ChangePasswordDTO changePasswordDTO);

    String login(SignInDTO signInDTO);

    void deleteUser(DeleteUserDTO deleteUserDTO);

    void logout(String accessToken);
}
