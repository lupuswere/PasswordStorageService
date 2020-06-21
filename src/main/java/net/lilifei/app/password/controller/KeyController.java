package net.lilifei.app.password.controller;

import net.lilifei.app.password.model.internal.PasswordRecord;
import net.lilifei.app.password.storage.PasswordStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
public class KeyController {

    private final PasswordStore passwordStore;

    public KeyController(final PasswordStore passwordStore) {
        this.passwordStore = passwordStore;
    }

    @GetMapping("/keys")
    public Set<String> getKeys(final HttpServletRequest httpServletRequest) {
        return passwordStore.getAllKeysByUserId("1234");
    }

    @GetMapping("/keys/{keyId}/passwords")
    public List<PasswordRecord> getAllPasswords(@PathVariable("keyId") final String keyId,
                                                final HttpServletRequest httpServletRequest) {
        return passwordStore.getAllRecordsByUserAndKey("1234", keyId);
    }
}
