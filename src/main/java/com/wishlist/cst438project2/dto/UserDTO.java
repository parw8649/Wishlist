package com.wishlist.cst438project2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user endpoint in UserController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
@NoArgsConstructor
public class UserDTO {

    private String id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String username;
    private String password;
    private String role;

    //This constructor is used for Admin and for user related API response
    public UserDTO(Long userId, String firstName, String lastName, String emailId, String username, String role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.username = username;
        this.role = role;
    }

    //This constructor is used for User TestCase - updateUser
    public UserDTO(String firstName, String lastName, String emailId, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.username = username;
        this.password = password;
    }
}
