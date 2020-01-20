package net.lilifei.app.password.controller;

import net.lilifei.app.password.model.PasswordRecord;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {

    @GetMapping("/password/{recordId}")
    public PasswordRecord getPassword(@PathVariable("recordId") final String recordId,
                                      @CookieValue("sid") final String sessionId) {
        return PasswordRecord.builder().recordId(recordId).build();
    }
}
