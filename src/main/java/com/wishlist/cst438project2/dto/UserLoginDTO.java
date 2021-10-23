package com.wishlist.cst438project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for login endpoint in UserController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    private UserDTO userDTO;
    private String accessToken;
}
