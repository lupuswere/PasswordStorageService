package net.lilifei.app.password.controller;

import lombok.extern.slf4j.Slf4j;
import net.lilifei.app.password.model.external.user.UserCreationRequest;
import net.lilifei.app.password.model.external.user.UserLoginRequest;
import net.lilifei.app.password.model.external.user.UserLoginResponse;
import net.lilifei.app.password.model.internal.UserRecord;
import net.lilifei.app.password.storage.UserStore;
import net.lilifei.app.password.util.HttpServletRequestProcessor;
import net.lilifei.app.password.util.UncheckedObjectMapper;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static net.lilifei.app.password.util.HttpSessionConstants.USER_ID;

@RestController
@Slf4j
public class UserController {

    private final UserStore userStore;

    private final BasicPasswordEncryptor passwordEncryptor;

    private final HttpServletRequestProcessor httpServletRequestProcessor;

    private final UncheckedObjectMapper uncheckedObjectMapper;

    public UserController(final UserStore userStore,
                          final BasicPasswordEncryptor passwordEncryptor,
                          final HttpServletRequestProcessor httpServletRequestProcessor,
                          final UncheckedObjectMapper uncheckedObjectMapper) {
        this.userStore = userStore;
        this.passwordEncryptor = passwordEncryptor;
        this.httpServletRequestProcessor = httpServletRequestProcessor;
        this.uncheckedObjectMapper = uncheckedObjectMapper;
    }

    @PostMapping(
            path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void createUser(final HttpServletRequest httpServletRequest) {
        final String body = httpServletRequestProcessor.getPostBody(httpServletRequest);
        final UserCreationRequest userCreationRequest =
                uncheckedObjectMapper.readValue(body, UserCreationRequest.class);
        final UserRecord userRecord = UserRecord.builder()
                .userId(userCreationRequest.getUserId())
                .password(passwordEncryptor.encryptPassword(userCreationRequest.getPassword()))
                .build();
        userStore.createUser(userRecord);
        log.info("Creating user {}.", userRecord.getUserId());
        httpServletRequest.getSession().setAttribute(USER_ID, userRecord.getUserId());
    }

    @PostMapping(
            path = "/users/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserLoginResponse validateUser(final HttpServletRequest httpServletRequest) {
        final String body = httpServletRequestProcessor.getPostBody(httpServletRequest);
        final UserLoginRequest userLoginRequest =
                uncheckedObjectMapper.readValue(body, UserLoginRequest.class);
        log.info("User {} trying to login...", userLoginRequest.getUserId());
        final UserRecord userRecord = userStore.getUser(userLoginRequest.getUserId());
        final boolean success = passwordEncryptor.checkPassword(userLoginRequest.getPassword(),
                userRecord.getPassword());
        log.info("Login status:{}.", success);
        if (success) {
            httpServletRequest.getSession().setAttribute(USER_ID, userRecord.getUserId());
        }
        return UserLoginResponse.builder()
                .status(success)
                .build();
    }

    @GetMapping("/users/check")
    public String check(final HttpServletRequest httpServletRequest) {
        final Object userIdObj = httpServletRequest.getSession().getAttribute(USER_ID);
        log.info("Checking status.");
        if (userIdObj == null) {
            log.info("User not logged in.");
            return "";
        }
        log.info("User {} checking status.", userIdObj);
        return (String) userIdObj;
    }

    @GetMapping("/users/logout")
    public void logout(final HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
    }

    @DeleteMapping(path = "/users/{userId}")
    public void deleteUser(@PathVariable("userId") final String userId,
                           final HttpServletRequest httpServletRequest) {
        final String userIdInSession = httpServletRequestProcessor.getUserId(httpServletRequest);
        if (!userIdInSession.equals(userId)) {
            throw new IllegalStateException("You don't have permission!");
        }
        log.info("Deleting user {}.", userId);
        userStore.deleteUser(userId);
    }
}
