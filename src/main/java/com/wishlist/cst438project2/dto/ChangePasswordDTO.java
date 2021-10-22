package com.wishlist.cst438project2.dto;

import lombok.Data;

/**
 * Data Transfer Object for change password endpoint in UserController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
public class ChangePasswordDTO {

    private String newPassword;
    private String confirmPassword;
}
