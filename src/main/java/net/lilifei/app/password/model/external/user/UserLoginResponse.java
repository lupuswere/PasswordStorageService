package net.lilifei.app.password.model.external.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginResponse {

    private boolean status;

    @JsonCreator
    private UserLoginResponse(@JsonProperty("status") final boolean status) {
        this.status = status;
    }
}
