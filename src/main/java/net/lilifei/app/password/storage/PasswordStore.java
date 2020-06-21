package net.lilifei.app.password.storage;

import net.lilifei.app.password.model.internal.PasswordRecord;

import java.util.List;
import java.util.Set;

public interface PasswordStore {

    PasswordRecord getRecordById(String recordId);

    Set<String> getAllKeysByUserId(String userId);

    List<PasswordRecord> getAllRecordsByUserAndKey(String userId, String key);

    List<PasswordRecord> getAllRecordsByUserId(String userId);

    void createRecord(PasswordRecord passwordRecord);

    void deleteRecord(String recordId);

    void deleteAllRecords(String userId);
}
