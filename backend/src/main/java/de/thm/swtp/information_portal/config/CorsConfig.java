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
        .addMapping("/**")
        .allowedOrigins("http://localhost:8001", "https://swtp.pcvolkmer.de", "https://swtp.pcvolkmer.de:8080")
        .allowedHeaders("*")
        .allowedMethods("GET", "POST", "PUT", "PATCH");
    }
}