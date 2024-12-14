package com.oauth2.oauth2;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostelController {

    @GetMapping("getData")
    public String getData() {
        System.out.println("test");
        return "data";
    }
}
