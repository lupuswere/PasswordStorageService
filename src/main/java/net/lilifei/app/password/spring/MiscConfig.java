package net.lilifei.app.password.spring;

import net.lilifei.app.password.util.HttpServletRequestProcessor;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiscConfig {

    @Bean
    HttpServletRequestProcessor postBodyGetter() {
        return new HttpServletRequestProcessor();
    }

    @Bean
    BasicPasswordEncryptor basicPasswordEncryptor() {
        return new BasicPasswordEncryptor();
    }
}
