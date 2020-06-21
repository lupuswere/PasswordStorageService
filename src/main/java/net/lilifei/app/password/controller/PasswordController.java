package net.lilifei.app.password.controller;

import net.lilifei.app.password.model.PasswordRecord;
import net.lilifei.app.password.model.password.PasswordCreationRequest;
import net.lilifei.app.password.storage.PasswordStore;
import net.lilifei.app.password.util.PostBodyGetter;
import net.lilifei.app.password.util.UncheckedObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class PasswordController {

    private final PasswordStore passwordStore;

    private final PostBodyGetter postBodyGetter;

    private final UncheckedObjectMapper uncheckedObjectMapper;

    public PasswordController(final PasswordStore passwordStore,
                              final PostBodyGetter postBodyGetter,
                              final UncheckedObjectMapper uncheckedObjectMapper) {
        this.passwordStore = passwordStore;
        this.postBodyGetter = postBodyGetter;
        this.uncheckedObjectMapper = uncheckedObjectMapper;
    }

    @GetMapping("/password/{recordId}")
    public PasswordRecord getPassword(@PathVariable("recordId") final String recordId,
                                      final HttpServletRequest httpServletRequest) {
        return passwordStore.getRecordById(recordId);
    }

    @PostMapping(path = "/password", consumes = "application/json", produces = "application/json")
    public void addPassword(final HttpServletRequest request) {
        final String body = postBodyGetter.getPostBody(request);
        final PasswordCreationRequest passwordCreationRequest =
                uncheckedObjectMapper.readValue(body, PasswordCreationRequest.class);
        final PasswordRecord passwordRecord = PasswordRecord.builder()
                .recordId(UUID.randomUUID().toString())
                .userId("1234")
                .key(passwordCreationRequest.getKey())
                .password(passwordCreationRequest.getPassword())
                .customizedFields(passwordCreationRequest.getCustomizedFields())
                .build();
        passwordStore.createRecord(passwordRecord);
    }
}
