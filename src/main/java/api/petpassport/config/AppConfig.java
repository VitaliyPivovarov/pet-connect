package api.petpassport.config;

import api.petpassport.exception.ExceptionsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ExceptionsHandler applicationExceptionsHandler() {
        return new ExceptionsHandler();
    }

}
