package net.lilifei.app.password.controller;

import net.lilifei.app.password.model.PingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public PingResult ping() {
        return PingResult.builder().healthy(true).build();
    }
}
