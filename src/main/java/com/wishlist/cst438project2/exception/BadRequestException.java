package com.wishlist.cst438project2.exception;

/**
 * Bad request exception
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

import com.wishlist.cst438project2.common.Constants;
import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpStatusException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, Constants.ERROR_BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
