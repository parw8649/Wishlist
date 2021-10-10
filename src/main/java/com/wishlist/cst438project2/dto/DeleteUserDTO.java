package com.wishlist.cst438project2.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeleteUserDTO {

    @NotEmpty(message = "username must not be null or empty")
    private String username;

    @NotEmpty(message = "username must not be null or empty")
    private String password;
}
