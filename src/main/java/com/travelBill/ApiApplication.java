package com.travelBill;

import com.travelBill.config.ApplicationConfiguration;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.dataSource(ApplicationConfiguration.getDbUrl(), ApplicationConfiguration.getDbUser(), ApplicationConfiguration.getDbPassword())
				.load();
		flyway.migrate();
		SpringApplication.run(ApiApplication.class, args);
	}
}
