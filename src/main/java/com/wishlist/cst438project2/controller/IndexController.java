package com.wishlist.cst438project2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/index")
public class IndexController {

    @GetMapping("/health/check")
    public String healthCheck() {
        return "Service is running!";
    }
}
