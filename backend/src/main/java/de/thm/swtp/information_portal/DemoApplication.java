package de.thm.swtp.information_portal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.repositories.QuestionRepository;

@SpringBootApplication
public class DemoApplication  {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}


	
