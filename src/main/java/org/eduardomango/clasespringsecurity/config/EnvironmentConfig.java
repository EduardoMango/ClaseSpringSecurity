package org.eduardomango.clasespringsecurity.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentConfig {

    static {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("jwt_secret", dotenv.get("JWT_SECRET"));
    }
}
