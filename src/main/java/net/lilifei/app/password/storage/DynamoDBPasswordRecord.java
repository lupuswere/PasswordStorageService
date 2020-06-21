package net.lilifei.app.password.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor // Needed by Mapper
@AllArgsConstructor
@Data
@DynamoDBTable(tableName = "password-record")
public class DynamoDBPasswordRecord {

    @DynamoDBHashKey(attributeName = "recordId")
    private String recordId;

    @DynamoDBAttribute(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userId-index", attributeName = "userId")
    private String userId;

    @DynamoDBAttribute(attributeName = "key")
    private String key;

    @DynamoDBAttribute(attributeName = "password")
    private String password;

    @DynamoDBAttribute(attributeName = "customizedFields")
    private String customizedFields;
}
