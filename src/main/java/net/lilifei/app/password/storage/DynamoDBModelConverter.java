package net.lilifei.app.password.storage;

import lombok.AllArgsConstructor;
import net.lilifei.app.password.model.internal.PasswordRecord;
import net.lilifei.app.password.model.internal.UserRecord;
import net.lilifei.app.password.util.UncheckedObjectMapper;

import java.util.Map;

@AllArgsConstructor
public class DynamoDBModelConverter {

    private final UncheckedObjectMapper objectMapper;

    public DynamoDBUserRecord fromUserRecord(final UserRecord userRecord) {
        return DynamoDBUserRecord.builder()
                .userId(userRecord.getUserId())
                .password(userRecord.getPassword())
                .build();
    }

    public UserRecord toUserRecord(final DynamoDBUserRecord dynamoDBUserRecord) {
        return UserRecord.builder()
                .userId(dynamoDBUserRecord.getUserId())
                .password(dynamoDBUserRecord.getPassword())
                .build();
    }

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
