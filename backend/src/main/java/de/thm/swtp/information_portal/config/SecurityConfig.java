package de.thm.swtp.information_portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.cors().and().authorizeRequests().anyRequest().authenticated().and()
        
        http.authorizeRequests().anyRequest().permitAll().and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwt -> {
                    
                    try {
                        System.out.println("TOKEN: "+ jwt.getTokenValue());
                        System.out.println(jwt.getClaims().toString());
                        // System.out.println("\nname: " +jwt.getClaimAsString("name"));
            
                        // System.out.println("given-name: "+ jwt.getClaimAsString("given_name"));
                        // System.out.println("family-name: "+jwt.getClaimAsString("family_name"));
                        // System.out.println("preferred-username: " +jwt.getClaimAsString("preferred_username"));
                        // System.out.println("email: "+jwt.getClaimAsString("email"));
            
                       
                    } catch(Exception e){
                        e.printStackTrace();
                    } 
                    var jwtAuthTOken = new JwtAuthenticationToken(jwt);
                    System.out.println("NEW Auth-TOKEN  "+ jwtAuthTOken);
                    jwtAuthTOken.setAuthenticated(true);
                    return jwtAuthTOken;
           
        });
        http.csrf().disable();
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    } 
}