package de.thm.swtp.information_portal;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Spring;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.models.Comment;
import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.CommentRepository;
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
	private final CommentRepository commentRepository;
	private final AnswerRepository answerRepository;
	
	public Runner(QuestionRepository questionRepository, CommentRepository commentRepository, AnswerRepository answerRepository) {
		this.questionRepository = questionRepository;
		this.commentRepository = commentRepository;
		this.answerRepository = answerRepository;
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		
		

		String tags[] = {"Java","Programmieren"};
		Question qq = new Question("Java", "Java ist SEHRRRR cool", tags);

		

		List<Answer> al = new ArrayList<Answer>();
		al.add(new Answer("contentasda ", 7));
		al.add(new Answer("asdadcontentasda ", 7));
		al.add(new Answer("contedsadadntasda ", 7));
		al.add(new Answer("contentaas   3t67mkl sda ", 7));
		al.add(new Answer("asdkazduasd75as78dz9pas0dhaspdäcontentasda ", 7));

		

		List<Comment> lc = new ArrayList<Comment>();
		lc.add(new Comment("Test Commentasdasd", "Alex", 4 ));	
		lc.add(new Comment("Teasdst Commenasdsadt", "Alex", 2));	
		lc.add(new Comment("Tesadasdst Commeasdadnt", "Alex", 1));	
		lc.add(new Comment("Teasdadst Commeasdasnt", "Alex", 0));

		Answers answers = new Answers(al, qq.getId());

		Comments cc = new Comments(lc, answers.getId());

		// questionRepository.save(qq);
		// commentRepository.save(cc);
		Answer aa = new Answer("Das soll ein neue Antwort sein ", 100);
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("name").is("appleA"));
		
		
		answerRepository.update(findQuery, updateQuery);

	}

}