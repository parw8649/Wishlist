package com.wishlist.cst438project2.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Http status exception
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */
@Getter
@Setter
public class HttpStatusException extends RuntimeException {
    private HttpStatus code;
    private String reason;
    private String errorCode; //optional

    public HttpStatusException(HttpStatus code, String reason) {
        super(String.format("HTTP %d - %s", code.value(), reason));
        this.code = code;
        this.reason = reason;
    }
}
