package de.thm.swtp.information_portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class RedirectConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry reg){
        // reg.addViewController("/info-portal/**").setViewName("index");
        reg.addRedirectViewController("/info-portal/ui/**", "/info-portal/ui");
    }
}