package net.lilifei.app.password.controller;

import net.lilifei.app.password.model.PasswordSnapshot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {

    @PostMapping("/password/{website}")
    public PasswordSnapshot getPassword() {
        return PasswordSnapshot.builder().build();
    }
}
