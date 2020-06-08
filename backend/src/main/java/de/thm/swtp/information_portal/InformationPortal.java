package de.thm.swtp.information_portal;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//https://spring.io/guides/gs/async-method/

@SpringBootApplication
@EnableAsync
public class InformationPortal  {
	public static void main(String[] args) {
		SpringApplication.run(InformationPortal.class, args);
	}
}


