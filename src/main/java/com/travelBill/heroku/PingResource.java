package com.travelBill.heroku;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingResource {

    @GetMapping("ping")
    public String ping() {
        return "PONG";
    }

}
