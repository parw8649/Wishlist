package com.wishlist.cst438project2.service;

import com.wishlist.cst438project2.dto.*;

/**
 * User service interface
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

public interface UserService {

    String saveUser(SignUpDTO signUpDTO);
    UserDTO updateUser(UserDTO userDTO);

    String changePassword(String username, ChangePasswordDTO changePasswordDTO);

    UserLoginDTO login(SignInDTO signInDTO);

    void deleteUser(DeleteUserDTO deleteUserDTO);

    void logout(String accessToken);
}
