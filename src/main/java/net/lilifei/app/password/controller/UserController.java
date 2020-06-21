package net.lilifei.app.password.controller;

import net.lilifei.app.password.model.external.user.UserCreationRequest;
import net.lilifei.app.password.model.external.user.UserLoginRequest;
import net.lilifei.app.password.model.external.user.UserLoginResponse;
import net.lilifei.app.password.model.internal.UserRecord;
import net.lilifei.app.password.storage.UserStore;
import net.lilifei.app.password.util.PostBodyGetter;
import net.lilifei.app.password.util.UncheckedObjectMapper;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    private final UserStore userStore;

    private final BasicPasswordEncryptor passwordEncryptor;

    private final PostBodyGetter postBodyGetter;

    private final UncheckedObjectMapper uncheckedObjectMapper;

    public UserController(final UserStore userStore,
                          final BasicPasswordEncryptor passwordEncryptor,
                          final PostBodyGetter postBodyGetter,
                          final UncheckedObjectMapper uncheckedObjectMapper) {
        this.userStore = userStore;
        this.passwordEncryptor = passwordEncryptor;
        this.postBodyGetter = postBodyGetter;
        this.uncheckedObjectMapper = uncheckedObjectMapper;
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public void createUser(final HttpServletRequest httpServletRequest) {
        final String body = postBodyGetter.getPostBody(httpServletRequest);
        final UserCreationRequest userCreationRequest =
                uncheckedObjectMapper.readValue(body, UserCreationRequest.class);
        final UserRecord userRecord = UserRecord.builder()
                .userId(userCreationRequest.getUserId())
                .password(passwordEncryptor.encryptPassword(userCreationRequest.getPassword()))
                .build();
        userStore.createUser(userRecord);
    }

    @PostMapping(path = "/users/login", consumes = "application/json", produces = "application/json")
    public UserLoginResponse validateUser(final HttpServletRequest httpServletRequest) {
        final String body = postBodyGetter.getPostBody(httpServletRequest);
        final UserLoginRequest userLoginRequest =
                uncheckedObjectMapper.readValue(body, UserLoginRequest.class);
        final UserRecord userRecord = userStore.getUser(userLoginRequest.getUserId());
        final boolean success = passwordEncryptor.checkPassword(userLoginRequest.getPassword(),
                userRecord.getPassword());
        return UserLoginResponse.builder()
                .status(success)
                .build();
    }

    @DeleteMapping(path = "/users/{userId}")
    public void deleteUser(@PathVariable("userId") final String userId,
                           final HttpServletRequest httpServletRequest) {
        userStore.deleteUser(userId);
    }
}
