package net.lilifei.app.password.controller;

import lombok.extern.slf4j.Slf4j;
import net.lilifei.app.password.model.internal.PasswordRecord;
import net.lilifei.app.password.model.external.password.PasswordCreationRequest;
import net.lilifei.app.password.storage.PasswordStore;
import net.lilifei.app.password.util.HttpServletRequestProcessor;
import net.lilifei.app.password.util.UncheckedObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class PasswordController {

    private final PasswordStore passwordStore;

    private final HttpServletRequestProcessor httpServletRequestProcessor;

    private final UncheckedObjectMapper uncheckedObjectMapper;

    public PasswordController(final PasswordStore passwordStore,
                              final HttpServletRequestProcessor httpServletRequestProcessor,
                              final UncheckedObjectMapper uncheckedObjectMapper) {
        this.passwordStore = passwordStore;
        this.httpServletRequestProcessor = httpServletRequestProcessor;
        this.uncheckedObjectMapper = uncheckedObjectMapper;
    }

    @GetMapping("/passwords/{recordId}")
    public PasswordRecord getPassword(@PathVariable("recordId") final String recordId,
                                      final HttpServletRequest httpServletRequest) {
        final String userId = httpServletRequestProcessor.getUserId(httpServletRequest);
        final PasswordRecord passwordRecord = passwordStore.getRecordById(recordId);
        if (!userId.equals(passwordRecord.getUserId())) {
            throw new IllegalArgumentException("You don't have permission!");
        }
        log.info("User {} trying to get record {}", userId, recordId);
        return passwordRecord;
    }

    @GetMapping("/passwords")
    public List<PasswordRecord> getPasswords(final HttpServletRequest httpServletRequest) {
        final String userId = httpServletRequestProcessor.getUserId(httpServletRequest);
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("You need to login!");
        }
        log.info("User {} trying to get records.", userId);
        return passwordStore.getAllRecordsByUserId(userId);
    }

    @PostMapping(
            path = "/passwords",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void addPassword(final HttpServletRequest request) {
        final String userId = httpServletRequestProcessor.getUserId(request);
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("You need to login!");
        }
        final String body = httpServletRequestProcessor.getPostBody(request);
        final PasswordCreationRequest passwordCreationRequest =
                uncheckedObjectMapper.readValue(body, PasswordCreationRequest.class);
        final PasswordRecord passwordRecord = PasswordRecord.builder()
                .recordId(UUID.randomUUID().toString())
                .userId(userId)
                .key(passwordCreationRequest.getKey())
                .password(passwordCreationRequest.getPassword())
                .customizedFields(passwordCreationRequest.getCustomizedFields())
                .build();
        log.info("User {} trying to create record for key {}.", userId, passwordRecord.getKey());
        passwordStore.createRecord(passwordRecord);
    }

    @DeleteMapping(path = "/passwords")
    public void deleteAllPasswords(final HttpServletRequest httpServletRequest) {
        final String userId = httpServletRequestProcessor.getUserId(httpServletRequest);
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("You need to login!");
        }
        log.info("User {} deleting all records.", userId);
        passwordStore.deleteAllRecords(userId);
    }

    @DeleteMapping(path = "/passwords/{recordId}")
    public void deletePassword(@PathVariable("recordId") final String recordId,
                               final HttpServletRequest httpServletRequest) {
        final String userId = httpServletRequestProcessor.getUserId(httpServletRequest);
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("You need to login!");
        }
        final PasswordRecord passwordRecord = passwordStore.getRecordById(recordId);
        if (!userId.equals(passwordRecord.getUserId())) {
            throw new IllegalArgumentException("You don't have permission!");
        }
        log.info("User {} deleting record {}.", userId, recordId);
        passwordStore.deleteRecord(recordId);
    }
}
