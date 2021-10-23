package com.wishlist.cst438project2.exception;

import org.springframework.http.HttpStatus;

/**
 * Not found exception
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

public class NotFoundException extends HttpStatusException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
