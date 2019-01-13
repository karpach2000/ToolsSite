package com.parcel.tools.db;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MigrationManager {

	@Autowired
	Flyway flyway;

	@PostConstruct
	public void init() {
		flyway.migrate();
	}
}
