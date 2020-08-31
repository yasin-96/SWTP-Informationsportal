package de.thm.swtp.information_portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwt -> {
                    var jwtAuthToken = new JwtAuthenticationToken(jwt);

                    //TODO BUG: need to set this manuel
                    jwtAuthToken.setAuthenticated(true);
//                    System.out.println(jwtAuthToken.toString());
//                    System.out.println("IS AUTH: " + jwtAuthToken.isAuthenticated());
                    return jwtAuthToken;

                });
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    } 
}