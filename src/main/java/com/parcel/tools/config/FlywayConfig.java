package com.parcel.tools.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
	@Autowired
	private DataSource dataSource;

	@Bean(name = "flyway")
	public Flyway getFlyway() {
		FluentConfiguration flywayConfig = Flyway.configure();

		flywayConfig.baselineVersion("1");
		flywayConfig.dataSource(dataSource);
		flywayConfig.baselineOnMigrate(true);
		flywayConfig.outOfOrder(true);
		flywayConfig.sqlMigrationSeparator("__");
		flywayConfig.table("Schema_version");
		flywayConfig.sqlMigrationPrefix("");

		System.out.println("CREATED FLYWAY");
		return new Flyway(flywayConfig);
	}
}
