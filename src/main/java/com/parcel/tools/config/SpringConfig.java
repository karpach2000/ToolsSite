package com.parcel.tools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;

import static com.parcel.tools.utils.FileUtils.userHomeDir;

@Configuration
public class SpringConfig {
	private String DB_DIR = userHomeDir() + File.separator + ".parcel";
	private String DB_PATH = DB_DIR + File.separator+ "lift.db";
	private String DB_URL = "jdbc:sqlite:"+DB_PATH;

	@Bean(name = "dataSource")
	public DataSource getSqlDataSource() {
		createDirsForDbIfNeeded();

		SQLiteDataSource src = new SQLiteDataSource();
		src.setUrl(DB_URL);

		return src;
	}

	private void createDirsForDbIfNeeded() {
		File file = new File(DB_DIR);
		file.mkdirs();
	}
}
