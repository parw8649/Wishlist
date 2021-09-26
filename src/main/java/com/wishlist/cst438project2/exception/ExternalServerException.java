package com.wishlist.cst438project2.exception;

import com.wishlist.cst438project2.common.Constants;
import org.springframework.http.HttpStatus;

public class ExternalServerException extends HttpStatusException {

    public ExternalServerException() {
        super(HttpStatus.BAD_GATEWAY, Constants.ERROR_BAD_GATEWAY);
    }

    public ExternalServerException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
