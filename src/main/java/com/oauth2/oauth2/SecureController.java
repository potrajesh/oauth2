package com.oauth2.oauth2;

import org.slf4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/api")
public class SecureController {
    private static final Logger logger = LoggerFactory.getLogger(SecureController.class);

    @GetMapping("secure") // Added base path for consistency
    public String securePage() {
        System.out.println("++++++++++++++++++++++++++");
        return "secure.html";
    }
    //after login success from github its call to below method
    @GetMapping("/") // /api/
    public String welcomePage(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            logger.info("User {} logged in successfully.", principal.getName());
        } else {
            logger.warn("Unauthorized access attempt.");
        }
        return "welcome.html";
    }
    @GetMapping("/unsecure")
    public String unsecureEndpoint() {
        return "This is an unsecure endpoint.";
    }
}
