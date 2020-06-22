package de.gca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaTestApplication {
	
	private static final Logger log = LoggerFactory.getLogger(JpaTestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JpaTestApplication.class, args);
	}

	
	
}
