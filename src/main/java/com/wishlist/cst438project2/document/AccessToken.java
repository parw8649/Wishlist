package com.wishlist.cst438project2.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * access token details
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {

    private Long userId;
    private String token;
}
