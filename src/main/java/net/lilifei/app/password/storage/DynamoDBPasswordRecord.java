package net.lilifei.app.password.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@DynamoDBTable(tableName = "password-record")
public class DynamoDBPasswordRecord {

    @DynamoDBHashKey(attributeName = "recordId")
    private final String recordId;

    @DynamoDBAttribute(attributeName = "userId")
    private final String userId;

    @DynamoDBAttribute(attributeName = "key")
    private final String key;

    @DynamoDBAttribute(attributeName = "password")
    private final String password;

    @DynamoDBAttribute(attributeName = "customizedFields")
    private final String customizedFields;
}
