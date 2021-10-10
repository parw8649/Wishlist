package com.wishlist.cst438project2.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.wishlist.cst438project2.dto.UserDTO;
import lombok.Data;

@Data
public class User {

    @DocumentId
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String username;
    private String password;

    public UserDTO fetchUserDTO() {

        return new UserDTO(userId, firstName, lastName, emailId, username);
    }
}
