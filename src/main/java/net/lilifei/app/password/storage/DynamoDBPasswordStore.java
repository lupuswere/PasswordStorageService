package net.lilifei.app.password.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import net.lilifei.app.password.model.internal.PasswordRecord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<String> getAllKeysByUserId(String userId) {
        final DynamoDBQueryExpression<DynamoDBPasswordRecord> query =
                new DynamoDBQueryExpression<DynamoDBPasswordRecord>()
                        .withIndexName("userId-index")
                        .withConsistentRead(false)
                        .withHashKeyValues(DynamoDBPasswordRecord.builder()
                                .userId(userId)
                                .build());
        final PaginatedList<DynamoDBPasswordRecord> records =
                dynamoDBMapper.query(DynamoDBPasswordRecord.class, query);
        return records.stream().map(DynamoDBPasswordRecord::getKey).collect(Collectors.toSet());
    }

    @Override
    public List<PasswordRecord> getAllRecordsByUserAndKey(String userId, String key) {
        final DynamoDBQueryExpression<DynamoDBPasswordRecord> query =
                new DynamoDBQueryExpression<DynamoDBPasswordRecord>()
                        .withIndexName("userId-index")
                        .withConsistentRead(false)
                        .withHashKeyValues(DynamoDBPasswordRecord.builder()
                                .userId(userId)
                                .build());
        final PaginatedList<DynamoDBPasswordRecord> records =
                dynamoDBMapper.query(DynamoDBPasswordRecord.class, query);
        return records.stream()
                .filter(r -> r.getKey().equals(key))
                .map(dynamoDBModelConverter::toPasswordRecord)
                .collect(Collectors.toList());
    }

    @Override
    public List<PasswordRecord> getAllRecordsByUserId(String userId) {
        final DynamoDBQueryExpression<DynamoDBPasswordRecord> query =
                new DynamoDBQueryExpression<DynamoDBPasswordRecord>()
                        .withIndexName("userId-index")
                        .withConsistentRead(false)
                        .withHashKeyValues(DynamoDBPasswordRecord.builder()
                                .userId(userId)
                                .build());
        final PaginatedList<DynamoDBPasswordRecord> records =
                dynamoDBMapper.query(DynamoDBPasswordRecord.class, query);
        return records.stream()
                .map(dynamoDBModelConverter::toPasswordRecord)
                .collect(Collectors.toList());
    }

    @Override
    public void createRecord(final PasswordRecord passwordRecord) {
        dynamoDBMapper.save(dynamoDBModelConverter.fromPasswordRecord(passwordRecord));
    }

    @Override
    public void deleteAllRecords(String userId) {
        final DynamoDBQueryExpression<DynamoDBPasswordRecord> query =
                new DynamoDBQueryExpression<DynamoDBPasswordRecord>()
                        .withIndexName("userId-index")
                        .withConsistentRead(false)
                        .withHashKeyValues(DynamoDBPasswordRecord.builder()
                                .userId(userId)
                                .build());
        final PaginatedList<DynamoDBPasswordRecord> records =
                dynamoDBMapper.query(DynamoDBPasswordRecord.class, query);
        dynamoDBMapper.batchDelete(records);
    }

    @Override
    public void deleteRecord(String recordId) {
        dynamoDBMapper.delete(dynamoDBMapper.load(DynamoDBPasswordRecord.class, recordId));
    }
}
