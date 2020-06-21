package net.lilifei.app.password.model.external.password;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PasswordCreationRequest {

    private String key;

    private String password;

    private Map<String, String> customizedFields;

    @JsonCreator
    private PasswordCreationRequest(@JsonProperty("key") final String key,
                                    @JsonProperty("password") final String password,
                                    @JsonProperty("customizedFields") final Map<String, String> customizedFields) {
        this.key = key;
        this.password = password;
        this.customizedFields = customizedFields;
    }
}
