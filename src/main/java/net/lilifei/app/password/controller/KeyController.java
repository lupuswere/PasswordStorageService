package net.lilifei.app.password.controller;

import lombok.extern.slf4j.Slf4j;

import net.lilifei.app.password.model.internal.PasswordRecord;
import net.lilifei.app.password.storage.PasswordStore;
import net.lilifei.app.password.util.HttpServletRequestProcessor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class KeyController {

    private final PasswordStore passwordStore;

    private final HttpServletRequestProcessor httpServletRequestProcessor;

    public KeyController(final PasswordStore passwordStore,
                         final HttpServletRequestProcessor httpServletRequestProcessor) {
        this.passwordStore = passwordStore;
        this.httpServletRequestProcessor = httpServletRequestProcessor;
    }

    @GetMapping("/keys")
    public Set<String> getKeys(final HttpServletRequest httpServletRequest) {
        final String userId = httpServletRequestProcessor.getUserId(httpServletRequest);
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("You need to login!");
        }
        log.info("User {} getting all keys.", userId);
        return passwordStore.getAllKeysByUserId(userId);
    }

    @GetMapping("/keys/{keyId}/passwords")
    public List<PasswordRecord> getAllPasswords(@PathVariable("keyId") final String keyId,
                                                final HttpServletRequest httpServletRequest) {
        final String userId = httpServletRequestProcessor.getUserId(httpServletRequest);
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("You need to login!");
        }
        log.info("User {} getting record for key {}.", userId, keyId);
        return passwordStore.getAllRecordsByUserAndKey(userId, keyId);
    }
}
