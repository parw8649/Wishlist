package com.wishlist.cst438project2.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String username;
    private String password;
    private String confirmPassword;
}
