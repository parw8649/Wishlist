package com.wishlist.cst438project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Data Transfer Object for Delete user endpoint in UserController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserDTO {

    @NotEmpty(message = "username must not be null or empty")
    private String username;

    @NotEmpty(message = "username must not be null or empty")
    private String password;
}
