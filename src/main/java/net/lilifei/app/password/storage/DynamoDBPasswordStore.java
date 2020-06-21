package net.lilifei.app.password.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import net.lilifei.app.password.model.internal.PasswordRecord;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DynamoDBPasswordStore implements PasswordStore {

    private final DynamoDBMapper dynamoDBMapper;

    private final DynamoDBModelConverter dynamoDBModelConverter;

    @Override
    public PasswordRecord getRecordById(String recordId) {
        return dynamoDBModelConverter.toPasswordRecord(dynamoDBMapper.load(DynamoDBPasswordRecord.class, recordId));
    }

    @Override
    public List<String> getAllKeysByUserId(String userId) {
        return null;
    }

    @Override
    public List<PasswordRecord> getAllRecordsByUserAndKey(String userId, String key) {
        return null;
    }

    @Override
    public void createRecord(final PasswordRecord passwordRecord) {
        dynamoDBMapper.save(dynamoDBModelConverter.fromPasswordRecord(passwordRecord));
    }

    @Override
    public void deleteAllRecords(String userId) {

    }

    @Override
    public void deleteRecord(String recordId) {

    }
}
