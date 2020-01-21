package net.lilifei.app.password.storage;

import net.lilifei.app.password.model.PasswordRecord;

import java.util.List;

public class DynamoDBPasswordStore implements PasswordStore {

    @Override
    public PasswordRecord getRecordById(String recordId) {
        return PasswordRecord.builder().recordId(recordId).build();
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
    public String createRecord(PasswordRecord passwordRecord) {
        return null;
    }

    @Override
    public void deleteAllRecords(String userId) {

    }

    @Override
    public void deleteRecord(String recordId) {

    }
}
