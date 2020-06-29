package de.thm.swtp.information_portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests().anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwt -> {
            System.out.println(jwt.toString());
            System.out.println(jwt.getClaims().toString());
            System.out.println("Fullname: " +jwt.getClaimAsString("name"));

            System.out.println("Firstname: "+ jwt.getClaimAsString("given_name"));
            System.out.println("Lastname: "+jwt.getClaimAsString("family_name"));
            System.out.println("Username: " +jwt.getClaimAsString("preferred_username"));
            System.out.println("Email: "+jwt.getClaimAsString("email"));


            

            return new JwtAuthenticationToken(jwt);
        });
        http.sessionManagement();
        // http.httpBasic();
        // http.csrf().disable();
    }
}