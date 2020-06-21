package net.lilifei.app.password.controller;

import lombok.extern.slf4j.Slf4j;
import net.lilifei.app.password.model.external.ping.PingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingController {

    @GetMapping("/ping")
    public PingResult ping() {
        log.info("Successfully received ping!");
        return PingResult.builder().healthy(true).build();
    }
}
