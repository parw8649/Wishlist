package com.wishlist.cst438project2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Service is running!";
    }

    // reference: https://stackoverflow.com/a/55286922
    @GetMapping("/")
    @ResponseBody
    public String showLanding() {
        return "landing_page";
    }
}
