package com.wishlist.cst438project2.exception;

import com.wishlist.cst438project2.common.Constants;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpStatusException {
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, Constants.ERROR_UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
