package net.lilifei.app.password.storage;

import net.lilifei.app.password.model.PasswordRecord;

import java.util.List;

public interface PasswordStore {

    List<String> getAllKeysByUserId(String userId);

    List<PasswordRecord> getAllRecordsByUserAndKey(String userId, String key);

    String createRecord(PasswordRecord passwordRecord);

    void deleteRecord(String recordId);

    void deleteAllRecords(String userId);
}
