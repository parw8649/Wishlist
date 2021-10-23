package com.wishlist.cst438project2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/**
 * Index Controller is used for wiring up HTML pages with Application Server
 *
 * references:
 *     - https://spring.io/projects/spring-boot
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Controller
public class IndexController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "health_check.html";
    }

    // reference: https://stackoverflow.com/a/66986791
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String showLanding() {
        return "landing_page.html";
    }

    @RequestMapping(path = "/login")
    public String showLoginPage() {
        return "login_page.html";
    }
}
