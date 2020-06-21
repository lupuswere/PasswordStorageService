package net.lilifei.app.password.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PasswordRecord {

    private String recordId;

    private String userId;

    private String key;

    private String password;

    private Map<String, String> customizedFields;
}
