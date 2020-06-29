package de.thm.swtp.information_portal;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


//https://spring.io/guides/gs/async-method/
@SpringBootApplication
@EnableAsync
public class InformationPortal   {
	public static void main(String[] args) {
		SpringApplication.run(InformationPortal.class, args);
	}

	// @Bean
	// CorsConfigurationSource corsConfigurationSource() {
	// 	var urlBased = new UrlBasedCorsConfigurationSource();

	// 	var corsSettings = new CorsConfiguration();

	// 	define header that will be allowed
	// 	corsSettings.setAllowedHeaders(Arrays.asList("*"));

	// 	define methode that allowed
	// 	corsSettings.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "DELET"));

	// 	define any origin that are allowed
	// 	corsSettings.setAllowedOrigins(Arrays.asList("*"));


	// 	urlBased.registerCorsConfiguration("/**", corsSettings);
	// 	return urlBased;
	// }
}


