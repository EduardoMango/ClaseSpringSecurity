package org.eduardomango.clasespringsecurity.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class EnvironmentConfig {

    static {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("jwt.secret", Objects.requireNonNull(dotenv.get("JWT_SECRET")));
    }
}
