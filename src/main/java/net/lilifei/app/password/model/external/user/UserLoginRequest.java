package net.lilifei.app.password.model.external.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginRequest {

    private String userId;

    private String password;

    @JsonCreator
    public UserLoginRequest(@JsonProperty("userId") final String userId,
                            @JsonProperty("password") final String password) {
        this.userId = userId;
        this.password = password;
    }
}
