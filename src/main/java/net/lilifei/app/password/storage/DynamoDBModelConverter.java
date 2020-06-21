package net.lilifei.app.password.storage;

import lombok.AllArgsConstructor;
import net.lilifei.app.password.model.PasswordRecord;
import net.lilifei.app.password.util.UncheckedObjectMapper;

import java.util.Map;

@AllArgsConstructor
public class DynamoDBModelConverter {

    private final UncheckedObjectMapper objectMapper;

    public DynamoDBPasswordRecord fromPasswordRecord(final PasswordRecord passwordRecord) {
        return DynamoDBPasswordRecord.builder()
                .recordId(passwordRecord.getRecordId())
                .userId(passwordRecord.getUserId())
                .key(passwordRecord.getKey())
                .password(passwordRecord.getPassword())
                .customizedFields(objectMapper.writeValueAsString(passwordRecord.getCustomizedFields()))
                .build();
    }

    public PasswordRecord toPasswordRecord(final DynamoDBPasswordRecord dynamoDBPasswordRecord) {
        return PasswordRecord.builder()
                .recordId(dynamoDBPasswordRecord.getRecordId())
                .userId(dynamoDBPasswordRecord.getUserId())
                .key(dynamoDBPasswordRecord.getKey())
                .password(dynamoDBPasswordRecord.getPassword())
                .customizedFields(objectMapper.readValue(dynamoDBPasswordRecord.getCustomizedFields(), Map.class))
                .build();
    }
}
