package com.wishlist.cst438project2.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String newPassword;
    private String confirmPassword;
}
