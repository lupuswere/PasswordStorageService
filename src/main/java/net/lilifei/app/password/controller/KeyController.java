package net.lilifei.app.password.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KeyController {

    @GetMapping("/keys")
    public List<String> getKeys() {
        return null;
    }
}
