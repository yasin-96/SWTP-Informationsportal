package de.thm.swtp.information_portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.repositories.OneAnswerRepository;

@ComponentScan(basePackages={"de.thm.swtp.information_portal"})
@DataMongoTest
class OneAnswerServiceTest {

	@Autowired
	private OneAnswerService oneAnswerService;
	
	@Autowired
	private OneAnswerRepository oneAnswerRepository;

	
	@Test
	public void getAnswerTest() {
		
		List<Answer> answersList= new ArrayList<Answer>();

		final Answer answerOne = new Answer("Answer1", 10);		
		final Answer answerTwo = new Answer("Answer2", 20);

		answersList.add(answerOne);
		answersList.add(answerTwo);	

		oneAnswerRepository.save(answerOne);
		oneAnswerRepository.save(answerTwo);		

		Optional<Answer> answersRes = oneAnswerService.getAnswer(answerOne.getId());
		
		Assertions.assertNotNull(answersRes);	
		Assertions.assertTrue(answersRes.isPresent());
		Assertions.assertEquals("Answer1", answersRes.get().getContent() );
		Assertions.assertEquals(10, answersRes.get().getRating() );	
	}
}
