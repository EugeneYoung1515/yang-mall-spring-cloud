package com.ywcjxf.mall.gatewayserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixFallback {

    @GetMapping("/fallback")
    public String fallback() {
        return "hystrix fallback";
    }

}
