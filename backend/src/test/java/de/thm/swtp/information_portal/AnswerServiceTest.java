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
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.service.AnswerService;

@ComponentScan(basePackages={"de.thm.swtp.information_portal"})
@DataMongoTest
class AnswerServiceTest {

	@Autowired
	private AnswerService answerService;

	@Test
	public void shouldPostAnswerTest() {
		List<Answer> answersList= new ArrayList<Answer>();

		final Answer answerOne = new Answer("Answer1", 10);		
		final Answer answerTwo = new Answer("Answer2", 20);

		answersList.add(answerOne);
		answersList.add(answerTwo);	

		Answers answers = new Answers(answersList, "Q1");
		Answers answersRes =  answerService.postAnswer(answers);
		
		Assertions.assertNotNull(answersRes ,"Successfully Post a Answer");	
	}
	
	@Test
	public void shouldFindByQuestionIdTest() {

		Optional<Answers> answersRes = answerService.findByQuestionId("Q1");
		
		Assertions.assertNotNull(answersRes);	
		Assertions.assertTrue(answersRes.isPresent());
		Assertions.assertEquals("Answer1", answersRes.get().getListOfAnswers().get(0).getContent() );
		Assertions.assertEquals(10, answersRes.get().getListOfAnswers().get(0).getRating() );	
	}
	
}
