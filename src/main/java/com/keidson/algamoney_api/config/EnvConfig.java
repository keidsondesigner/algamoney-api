// filepath: /d:/dev/Java e SpringBoot Projetos/algamoney-projetos/algamoney-api/src/main/java/com/keidson/algamoney_api/config/EnvConfig.java
package com.keidson.algamoney_api.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}