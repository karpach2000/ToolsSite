package com.parcel.tools.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select login , pas, NOT deleted from Users where login = ?")
				.authoritiesByUsernameQuery(
						"select u.login, r.role from Users u " +
								"INNER JOIN Users_roles ur ON(ur.user_id = u.id)" +
								"INNER JOIN Roles r ON (r.id = ur.role_id) " +
								"where login = ?")
		.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/mqtt/login").permitAll()
				.antMatchers("/mqtt/**").hasRole("ADMIN")
				.and()
				.formLogin()
				.loginPage("/mqtt/login")
				.defaultSuccessUrl("/mqtt/control")
				.failureUrl("/mqtt/login?error")
				.usernameParameter("login").passwordParameter("password")
				.and()
				.logout().logoutUrl("/mqtt/logout").logoutSuccessUrl("/mqtt/login?logout")
				.and()
				.exceptionHandling().accessDeniedPage("/403")
				.and()
				.csrf();
	}

	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
