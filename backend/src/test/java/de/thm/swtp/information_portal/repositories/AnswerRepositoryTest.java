//package de.thm.swtp.information_portal.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//
//import de.thm.swtp.information_portal.models.Answer.Answer;
//import de.thm.swtp.information_portal.models.Answer.Answers;
//import de.thm.swtp.information_portal.repositories.AnswerRepository;
//
//@DataMongoTest
//class AnswerRepositoryTest {
//
//	private static final Logger LOGGER=LoggerFactory.getLogger(AnswerRepositoryTest.class);
//
//	@Autowired
//	private AnswerRepository  answerRepository;
//
//	@BeforeEach
//	public void setUp() {
//		 answerRepository.deleteAll();
//	}
//
//	@Test
//	public void shouldInsertAnswerTest() {
//
//		LOGGER.info("\n\n **********Start insert Answer Test********** \n\n");
//
//		List<Answer> answersList= new ArrayList<Answer>();
//
//		final Answer answerOne = new Answer("Answer1", 10);
//		final Answer answerTwo = new Answer("Answer2", 20);
//
//		answersList.add(answerOne);
//		answersList.add(answerTwo);
//
//		Answers answers = new Answers(answersList, "Q1");
//		Answers answersRes = answerRepository.save(answers);
//		Assertions.assertEquals(answers.getId(), answersRes.getId(), "Ids should be equals");
//		Assertions.assertNotNull(answersRes , "Answers should be not null");
//
//		LOGGER.info("\n\n **********End insertAnswer Test********** \n\n");
//	}
//
//
//	@Test
//	public void shouldGetInswerByIdTest() {
//
//		LOGGER.info("\n\n **********Start getAnswerById Test********** \n\n");
//
//		List<Answer> answersList= new ArrayList<Answer>();
//
//		final Answer answerOne = new Answer("Answer1", 10);
//		final Answer answerTwo = new Answer("Answer2", 20);
//
//		answersList.add(answerOne);
//		answersList.add(answerTwo);
//
//		Answers answers = new Answers(answersList, "Q1");
//		answerRepository.save(answers);
//
//		Assertions.assertEquals("Q1" , answerRepository.findById("Q1").get().getId(),
//				"Successfully fetched a answer by Question ID");
//
//		LOGGER.info("\n\n **********End getAnswerById Test********** \n\n");
//	}
//
//	@Test
//	public void shouldUpdateAnswerTest() {
//
//		LOGGER.info("\n\n **********Start updateAnswer Test********** \n\n");
//
//		List<Answer> answersList= new ArrayList<Answer>();
//
//		final Answer answerOne = new Answer("Answer1", 10);
//		final Answer answerTwo = new Answer("Answer2", 20);
//
//		answersList.add(answerOne);
//		answersList.add(answerTwo);
//
//		Answers answers = new Answers(answersList, "Q1");
//		answerRepository.save(answers);
//
//		answerTwo.setContent("Answer3");
//		answerTwo.setRating(12);
//		Assertions.assertEquals(answerTwo.getContent(), answerRepository.save(answers).getListOfAnswers().get(1).getContent(),
//				"Successfully updated a Answer");
//
//		LOGGER.info("\n\n **********End updateAnswer Test********** \n\n");
//	}
//
//	@Test
//	public void shouldGetAllAnswersTest() {
//
//		LOGGER.info("\n\n **********Start getAllAnswers Test********** \n\n");
//
//		List<Answer> answersList= new ArrayList<Answer>();
//
//		final Answer answerOne = new Answer("Answer1", 10);
//		final Answer answerTwo = new Answer("Answer2", 20);
//
//		answersList.add(answerOne);
//		answersList.add(answerTwo);
//
//		Answers answers = new Answers(answersList, "Q1");
//		answerRepository.save(answers);
//
//		System.out.println(answerRepository.findAll().size());
//		Assertions.assertEquals(1, answerRepository.findAll().size(),
//				"Successfully fetched the list a answers");
//
//		LOGGER.info("\n\n **********End getAllAnswers Test********** \n\n");
//	}
//
//	@Test
//	public void shouldDeleteAnswerTest() {
//
//		LOGGER.info("\n\n **********Start deleteAnswer Test********** \n\n");
//
//		List<Answer> answersList= new ArrayList<Answer>();
//
//		final Answer answerOne = new Answer("Answer1", 10);
//		final Answer answerTwo = new Answer("Answer2", 20);
//
//		answersList.add(answerOne);
//		answersList.add(answerTwo);
//
//		Answers answers = new Answers(answersList, "Q1");
//		answerRepository.save(answers);
//
//		answerRepository.deleteById("Q1");
//		Assertions.assertEquals(Optional.empty(), answerRepository.findById("Q1"),
//				"Successfully deleted a single a comment");
//
//		LOGGER.info("\n\n **********End deleteAnswer Test********** \n\n");
//	}
//
//}
