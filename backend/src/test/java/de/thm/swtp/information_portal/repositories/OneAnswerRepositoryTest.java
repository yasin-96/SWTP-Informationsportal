package de.thm.swtp.information_portal.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import de.thm.swtp.information_portal.models.Answer.Answer;

@DataMongoTest
class OneAnswerRepositoryTest {

	private static final Logger LOGGER=LoggerFactory.getLogger(OneAnswerRepositoryTest.class);

	@Autowired
	private OneAnswerRepository  oneAnswerRepository;

	@BeforeEach
	public void setUp() {
		oneAnswerRepository.deleteAll();
	}

	@Test
	public void shouldInsertOneAnswerTest() {

		LOGGER.info("\n\n **********Start insert One Answer Test********** \n\n");

		final Answer answerOne = new Answer("Answer1", 10 , "user1",  "user1");
		final Answer answerTwo = new Answer("Answer2", 12 , "user2",  "user2");

		Answer answersResOne = oneAnswerRepository.save(answerOne);
		Answer answersResTwo = oneAnswerRepository.save(answerTwo);
		
		Assertions.assertEquals(answerOne.getId(), answersResOne.getId(), "Ids should be equals");
		Assertions.assertEquals(answerTwo.getId(), answersResTwo.getId(), "Ids should be equals");

		Assertions.assertNotNull(answersResOne , "AnswerOne should be not null");
		Assertions.assertNotNull(answersResTwo , "AnswerTwo should be not null");

		Assertions.assertEquals("Answer1", answersResOne.getContent());
		Assertions.assertEquals("Answer2", answersResTwo.getContent());
		
		LOGGER.info("\n\n **********End insert One Answer Test********** \n\n");
	}


	@Test
	public void shouldGetOneInswerByIdTest() {

		LOGGER.info("\n\n **********Start GetOneInswerById Test********** \n\n");

		final Answer answerOne = new Answer("Answer1", 10 , "user1",  "user1");

		Answer answer = oneAnswerRepository.save(answerOne);

		Assertions.assertEquals("Answer1" , oneAnswerRepository.findById(answer.getId()).get().getContent(),
				"Successfully fetched a answer by Question ID");

		LOGGER.info("\n\n **********End GetOneInswerById Test********** \n\n");
	}

	@Test
	public void shouldUpdateOneAnswerTest() {

		LOGGER.info("\n\n **********Start updateOneAnswer Test********** \n\n");


		final Answer answerOne = new Answer("Answer1", 10 , "user1",  "user1");

		oneAnswerRepository.save(answerOne);
		
		answerOne.setContent("Answer3");
		answerOne.setRating(12);
		
		Assertions.assertEquals("Answer3", oneAnswerRepository.save(answerOne).getContent(),
				"Successfully updated a Answer");

		LOGGER.info("\n\n **********End updateOneAnswer Test********** \n\n");
	}

	@Test
	public void shouldGetAllAnswersTest() {

		LOGGER.info("\n\n **********Start getAllAnswers Test********** \n\n");

		final Answer answerOne = new Answer("Answer1", 10 , "user1",  "user1");
		final Answer answerTwo = new Answer("Answer2", 12 , "user2",  "user2");

		oneAnswerRepository.save(answerOne);
		oneAnswerRepository.save(answerTwo);

		List<Answer> answers = oneAnswerRepository.findAll();
		
		Assertions.assertEquals(2, answers.size(),
				"Successfully fetched the list a answers");
		Assertions.assertEquals("Answer1" , answers.get(0).getContent());

		LOGGER.info("\n\n **********End getAllAnswers Test********** \n\n");
	}

	@Test
	public void shouldDeleteOneAnswerTest() {

		LOGGER.info("\n\n **********Start deleteOneAnswer Test********** \n\n");

		final Answer answerOne = new Answer("Answer1", 10 , "user1",  "user1");
		final Answer answerTwo = new Answer("Answer2", 12 , "user2",  "user2");

		Answer answer = oneAnswerRepository.save(answerOne);
		oneAnswerRepository.save(answerTwo);
		
		oneAnswerRepository.deleteById(answer.getId());
		
		Assertions.assertEquals(Optional.empty(), oneAnswerRepository.findById(answer.getId()),
				"Successfully deleted a single a comment");

		LOGGER.info("\n\n **********End deleteAnswer Test********** \n\n");
	}

}
