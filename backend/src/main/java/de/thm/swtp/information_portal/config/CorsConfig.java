package de.thm.swtp.information_portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
        .addMapping("/**").allowCredentials(true)
        .allowedOrigins("*").allowedHeaders("*")
        // .allowedMethods("GET","PUT", "POST", "OPTIONS");
        .allowedMethods("*");
    }
    
}