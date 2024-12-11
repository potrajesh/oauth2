package com.oauth2.oauth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SecureController {

    @GetMapping("/test") // Added base path for consistency
    public String securePage() {
        System.out.println("++++++++++++++++++++++++++");
        return "secure.html";
    }
    //after login success from github its call to below method
    @GetMapping("/") // Added base path for consistency
    public String welComePage() {
        System.out.println("++++++++++++++++++++++++++");
        return "welcome.html";
    }
}
