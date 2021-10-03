package com.wishlist.cst438project2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    private String emailId;
    private String username;

    public UserDTO(String firstName, String lastName, String emailId, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.username = username;
    }
}
