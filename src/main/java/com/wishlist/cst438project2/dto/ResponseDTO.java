package com.wishlist.cst438project2.dto;

import lombok.Data;

/**
 * Data Transfer Object for response in UserController.java
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Data
public class ResponseDTO<T> {

    private int status;
    private T Data;
    private String message;
}