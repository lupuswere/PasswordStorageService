package net.lilifei.app.password.controller;

import net.lilifei.app.password.model.PasswordRecord;
import net.lilifei.app.password.storage.PasswordStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {

    private final PasswordStore passwordStore;

    public PasswordController(PasswordStore passwordStore) {
        this.passwordStore = passwordStore;
    }

    @GetMapping("/password/{recordId}")
    public PasswordRecord getPassword(@PathVariable("recordId") final String recordId,
                                      @CookieValue("sid") final String sessionId) {
        return passwordStore.getRecordById(recordId);
    }
}
