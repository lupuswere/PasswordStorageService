package net.lilifei.app.password.storage;

import net.lilifei.app.password.model.internal.UserRecord;

public interface UserStore {

    void createUser(UserRecord userRecord);

    void deleteUser(String userId);

    UserRecord getUser(String userId);

    void updateUser(UserRecord userRecord);
}
