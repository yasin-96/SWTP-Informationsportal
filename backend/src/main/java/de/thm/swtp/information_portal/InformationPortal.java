package de.thm.swtp.information_portal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Comment;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.repositories.QuestionRepository;

@SpringBootApplication
public class InformationPortal  {
	public static void main(String[] args) {
		SpringApplication.run(InformationPortal.class, args);
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
		
		List<Comment> lc = new ArrayList<Comment>();
		lc.add(new Comment("Test Commentasdasd", "Alex", 4));	
		lc.add(new Comment("Teasdst Commenasdsadt", "Alex", 2));	
		lc.add(new Comment("Tesadasdst Commeasdadnt", "Alex", 1));	
		lc.add(new Comment("Teasdadst Commeasdasnt", "Alex", 0));

		List<Answer> answer = new ArrayList<Answer>();
		answer.add(new Answer("contentasda ", lc, 7, "00"));
		answer.add(new Answer("asdadcontentasda ", lc, 7, "00"));
		answer.add(new Answer("contedsadadntasda ", lc, 7, "00"));
		answer.add(new Answer("contentaas   3t67mkl sda ", lc, 7, "00"));
		answer.add(new Answer("asdkazduasd75as78dz9pas0dhaspdäcontentasda ", lc, 7, "00"));

		String tags[] = {"1","2"};
		questionRepository.save(new Question("Java", "Java ist cool", tags, answer));
	}

}