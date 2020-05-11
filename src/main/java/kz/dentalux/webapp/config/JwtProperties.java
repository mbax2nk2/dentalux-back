package kz.dentalux.webapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    private String secretKey = "secret";

    //validity in milliseconds
    private long validityInMs = 43_200_000; // 1h
}

