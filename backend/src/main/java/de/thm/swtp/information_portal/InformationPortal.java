package de.thm.swtp.information_portal;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;



//https://spring.io/guides/gs/async-method/
@SpringBootApplication
@EnableAsync
public class InformationPortal extends WebSecurityConfigurerAdapter  {
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
		// http.cors().and().oauth2ResourceServer().jwt().jwtAuthenticationConverter((jwt) -> {
		// 	System.out.println(jwt.getClaimAsString("name"));

		// 	return new JwtAuthenticationToken(jwt);
		// });
		
		// http.cors().and().oauth2ResourceServer().jwt().jwtAuthenticationConverter((jwt) -> new JwtAuthenticationToken(jwt));
		http.cors().and().csrf().disable();
	}
}


// @Configuration
// class SecurityConfig  {

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests().anyRequest().permitAll()
//                 .and()
//                 .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwt -> {

//                      /** Pseudocode .... Bearbeitung imer bei jedem Request
//                      User user = userRepository.findById(jwt.getSubject());
//                      if (user != null) {
//                         ... Aktualisieren
//                      } else {
//                         ... Erstmalig speichern
//                         userRepositry.save(new User(jwt.getSubject(), getClaimAsString("name")))
//                      }

//                     */

//             System.out.println(jwt.getClaimAsString("name"));
//             return new JwtAuthenticationToken(jwt);
//         });

//         http.csrf().disable();
//     }
// }