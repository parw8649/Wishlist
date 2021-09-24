package com.wishlist.cst438project2.document;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    @DocumentId
    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String username;
    private String password;

    public User(String firstName, String lastName, String emailId, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.username = username;
        this.password = password;
    }
}
