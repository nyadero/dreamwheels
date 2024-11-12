package com.dreamwheels.dreamwheels;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Objects;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableCaching
//@EnableAdminServer
public class Application {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		// datasource
		System.setProperty("SPRING_DATASOURCE_URL", Objects.requireNonNull(dotenv.get("SPRING_DATASOURCE_URL")));
		System.setProperty("SPRING_DATASOURCE_USERNAME", Objects.requireNonNull(dotenv.get("SPRING_DATASOURCE_USERNAME")));
		System.setProperty("SPRING_DATASOURCE_PASSWORD", Objects.requireNonNull(dotenv.get("SPRING_DATASOURCE_PASSWORD")));

		// jwt
		System.setProperty("JWT_SECRET_KEY", Objects.requireNonNull(dotenv.get("JWT_SECRET_KEY")));
		System.setProperty("JWT_EXPIRATION", Objects.requireNonNull(dotenv.get("JWT_EXPIRATION")));
		System.setProperty("JWT_REFRESH_TOKEN_EXPIRATION", Objects.requireNonNull(dotenv.get("JWT_REFRESH_TOKEN_EXPIRATION")));

		SpringApplication.run(Application.class, args);
	}

}
