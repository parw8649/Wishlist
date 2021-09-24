package com.wishlist.cst438project2.exception;

import com.wishlist.cst438project2.Common.Constants;
import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpStatusException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, Constants.ERROR_BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
