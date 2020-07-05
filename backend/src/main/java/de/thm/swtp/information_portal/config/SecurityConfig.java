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
        // http.cors().and().authorizeRequests().anyRequest().authenticated().and()
        
        http.authorizeRequests().anyRequest().permitAll().and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwt -> {
                    // System.out.println("TOKEN: "+ jwt.getTokenValue());
                    // System.out.println(jwt.getClaims().toString());
            
                    var jwtAuthToken = new JwtAuthenticationToken(jwt);
                    jwtAuthToken.setAuthenticated(true);
                    return jwtAuthToken;
           
        });
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    } 
}