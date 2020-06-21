package net.lilifei.app.password.storage;

import net.lilifei.app.password.model.internal.PasswordRecord;

import java.util.List;

public interface PasswordStore {

    PasswordRecord getRecordById(String recordId);

    List<String> getAllKeysByUserId(String userId);

    List<PasswordRecord> getAllRecordsByUserAndKey(String userId, String key);

    void createRecord(PasswordRecord passwordRecord);

    void deleteRecord(String recordId);

    void deleteAllRecords(String userId);
}
