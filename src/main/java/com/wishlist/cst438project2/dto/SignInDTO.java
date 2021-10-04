package com.wishlist.cst438project2.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignInDTO {

    @NotEmpty(message = "username must not be null or empty")
    private String username;

    @NotEmpty(message = "username must not be null or empty")
    private String password;
}
