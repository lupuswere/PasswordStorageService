package net.lilifei.app.password.spring;

import net.lilifei.app.password.storage.DynamoDBPasswordStore;
import net.lilifei.app.password.storage.PasswordStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    PasswordStore passwordStore() {
        return new DynamoDBPasswordStore();
    }
}
