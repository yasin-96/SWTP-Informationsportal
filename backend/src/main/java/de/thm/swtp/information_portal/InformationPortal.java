package de.thm.swtp.information_portal;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.oauth2
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

	@Configuration
	class SecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll()
					.and()
					.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwt -> {
				System.out.println(jwt.getClaimAsString("name"));
				return new JwtAuthenticationToken(jwt);
			});
		}
	}
}

