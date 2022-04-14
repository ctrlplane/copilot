package io.ctrlplane.copilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/** The application entry point. */
@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories
public class Application {

	/**
	 * Main method.
	 *
	 * @param args The application arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
