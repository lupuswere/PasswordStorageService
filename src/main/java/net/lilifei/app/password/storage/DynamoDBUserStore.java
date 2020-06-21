package net.lilifei.app.password.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import net.lilifei.app.password.model.internal.UserRecord;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DynamoDBUserStore implements UserStore {

    private final DynamoDBMapper dynamoDBMapper;

    private final DynamoDBModelConverter dynamoDBModelConverter;

    @Override
    public void createUser(final UserRecord userRecord) {
        final DynamoDBUserRecord dynamoDBUserRecord =
                dynamoDBMapper.load(DynamoDBUserRecord.class, userRecord.getUserId());
        if(dynamoDBUserRecord != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        dynamoDBMapper.save(dynamoDBModelConverter.fromUserRecord(userRecord));
    }

    @Override
    public void updateUser(final UserRecord userRecord) {
        final DynamoDBUserRecord dynamoDBUserRecord =
                dynamoDBMapper.load(DynamoDBUserRecord.class, userRecord.getUserId());
        if(dynamoDBUserRecord == null) {
            throw new IllegalArgumentException("User doesn't exist!");
        }
        dynamoDBMapper.save(dynamoDBModelConverter.fromUserRecord(userRecord));
    }

    @Override
    public void deleteUser(final String userId) {
        final DynamoDBUserRecord dynamoDBUserRecord =
                dynamoDBMapper.load(DynamoDBUserRecord.class, userId);
        if(dynamoDBUserRecord == null) {
            throw new IllegalArgumentException("User doesn't exist!");
        }
        dynamoDBMapper.delete(dynamoDBUserRecord);
    }

    @Override
    public UserRecord getUser(final String userId) {
        final DynamoDBUserRecord dynamoDBUserRecord =
                dynamoDBMapper.load(DynamoDBUserRecord.class, userId);
        if(dynamoDBUserRecord == null) {
            throw new IllegalArgumentException("User doesn't exist!");
        }
        return dynamoDBModelConverter.toUserRecord(dynamoDBUserRecord);
    }
}
