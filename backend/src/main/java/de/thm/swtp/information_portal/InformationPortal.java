package de.thm.swtp.information_portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InformationPortal {

    public static void main(String[] args) {
        SpringApplication.run(InformationPortal.class, args);
    }

}
