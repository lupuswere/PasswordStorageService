package net.lilifei.app.password.model.internal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRecord {

    private String userId;

    private String password;
}
