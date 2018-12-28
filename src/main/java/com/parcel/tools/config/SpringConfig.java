package com.parcel.tools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.util.Properties;

import static com.parcel.tools.utils.FileUtils.userHomeDir;

@Configuration
public class SpringConfig {
	private String DB_PATH = "mqtt.db";
	private String DB_URL = "jdbc:sqlite:"+DB_PATH;

	@Bean(name = "dataSource")
	public DataSource getSqlDataSource() {
		createDirsForDbIfNeeded();

		SQLiteDataSource src = new SQLiteDataSource();
		src.setUrl(DB_URL);

		return src;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em
				= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getSqlDataSource());
		em.setPackagesToScan(new String[] { "com.parcel.tools.mqtt" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(jpaProperties());

		return em;
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");

		return properties;
	}

	private void createDirsForDbIfNeeded() {
		File file = new File(DB_PATH);
		file.mkdirs();
	}
}
