package de.thm.swtp.information_portal;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.var;

//https://spring.io/guides/gs/async-method/
@SpringBootApplication
@EnableAsync

@EnableWebSecurity
public class InformationPortal extends WebSecurityConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(InformationPortal.class, args);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var urlBased = new UrlBasedCorsConfigurationSource();

		var corsSettings = new CorsConfiguration();

		//define header that will be allowed
		corsSettings.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "origin", "content-type", "accept", "x-requested-with"));

		//define methode that allowed
		corsSettings.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "DELET"));

		//define any origin that are allowed
		corsSettings.setAllowedOrigins(Arrays.asList("*"));
		

		urlBased.registerCorsConfiguration("/**", corsSettings);
		return urlBased;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and().csrf().disable();
	}

}