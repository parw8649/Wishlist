package com.wishlist.cst438project2.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.wishlist.cst438project2.common.Constants;
import com.wishlist.cst438project2.common.Utils;
import com.wishlist.cst438project2.dto.UserDTO;
import lombok.Data;
import org.apache.tomcat.util.bcel.Const;

@Data
public class User {

    @DocumentId
    private String id;

    //Added to generate unique id required for user
    private String userId = Utils.generateId(Constants.KEY_USER_ID);
    private String firstName;
    private String lastName;
    private String emailId;
    private String username;
    private String password;

    public UserDTO fetchUserDTO() {

        return new UserDTO(userId, firstName, lastName, emailId, username);
    }
}
