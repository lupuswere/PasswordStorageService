package net.lilifei.app.password.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordSnapshot {

    private String recordId;

    private String userId;

    private String website;

    private String password;
}
