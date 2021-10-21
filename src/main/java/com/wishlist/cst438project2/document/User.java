package com.wishlist.cst438project2.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.dto.UserDTO;
import com.wishlist.cst438project2.enums.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    @DocumentId
    private String id;

    //Added to generate unique id required for user
    private Long userId = Utils.generateId();
    private String firstName;
    private String lastName;
    private String emailId;
    private String username;
    private String password;
    private RoleType role = RoleType.USER;

    public UserDTO fetchUserDTO() {

        return new UserDTO(userId, firstName, lastName, emailId, username, role.getValue());
    }
}
