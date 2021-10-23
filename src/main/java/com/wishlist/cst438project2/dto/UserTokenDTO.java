package com.wishlist.cst438project2.dto;

import lombok.Data;

/**
 * Data Transfer Object for access token implementation in UserController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
public class UserTokenDTO {

    private Long userId;
    private String emailId;
    private String username;
    private String role;
}
