package com.wishlist.cst438project2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String healthCheck() {
        return "Home Page";
    }
}
