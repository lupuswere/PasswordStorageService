package net.lilifei.app.password.spring;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import net.lilifei.app.password.storage.DynamoDBModelConverter;
import net.lilifei.app.password.storage.DynamoDBPasswordStore;
import net.lilifei.app.password.storage.PasswordStore;
import net.lilifei.app.password.util.UncheckedObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Bean
    @Autowired
    PasswordStore passwordStore(final DynamoDBMapper dynamoDBMapper,
                                final DynamoDBModelConverter dynamoDBModelConverter) {
        return DynamoDBPasswordStore.builder()
                .dynamoDBMapper(dynamoDBMapper)
                .dynamoDBModelConverter(dynamoDBModelConverter)
                .build();
    }

    @Bean
    @Autowired
    DynamoDBModelConverter dynamoDBModelConverter(final UncheckedObjectMapper uncheckedObjectMapper) {
        return new DynamoDBModelConverter(uncheckedObjectMapper);
    }

    @Bean
    @Autowired
    DynamoDBMapper dynamoDBMapper(final AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Bean
    AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return accessKey;
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return secretKey;
                    }
                }))
                .build();
    }
}
