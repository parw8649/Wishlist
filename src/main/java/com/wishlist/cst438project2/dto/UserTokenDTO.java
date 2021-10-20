package com.wishlist.cst438project2.dto;

import lombok.Data;

@Data
public class UserTokenDTO {

    private Long userId;
    private String emailId;
    private String username;
    private String role;
}
