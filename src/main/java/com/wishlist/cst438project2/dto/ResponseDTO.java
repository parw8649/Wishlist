package com.wishlist.cst438project2.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {

    private int status;
    private T Data;
    private String message;
}