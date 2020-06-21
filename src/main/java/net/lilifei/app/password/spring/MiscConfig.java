package net.lilifei.app.password.spring;

import net.lilifei.app.password.util.PostBodyGetter;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiscConfig {

    @Bean
    PostBodyGetter postBodyGetter() {
        return new PostBodyGetter();
    }

    @Bean
    BasicPasswordEncryptor basicPasswordEncryptor() {
        return new BasicPasswordEncryptor();
    }
}
