package de.thm.swtp.information_portal;

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

@Component
class Runner implements CommandLineRunner {

private final QuestionRepository questionRepository;
	
	public Runner(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		
		
	String tags[] = {"1","2"};
		questionRepository.save(new Question("Java", "Java ist cool", tags, new Long(12)));
	}

}
	
