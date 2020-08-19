package de.thm.swtp.information_portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InformationPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationPortalApplication.class, args);
    }

}
