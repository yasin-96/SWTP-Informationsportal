//package de.thm.swtp.information_portal.Services;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import de.thm.swtp.information_portal.controller.AnswerController;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.context.annotation.ComponentScan;
//
//import de.thm.swtp.information_portal.models.Answer;
//import de.thm.swtp.information_portal.models.Answers;
//import de.thm.swtp.information_portal.service.AnswerService;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static de.thm.swtp.information_portal.Util.Util.generateJWTToken;
//
//@ComponentScan(basePackages={"de.thm.swtp.information_portal"})
//@DataMongoTest
//class AnswerServiceTest {
//
//	@Autowired
//	private AnswerService answerService;
//
//	private String uuidForQuestion;
//	private String uuidForAnswerOne;
//	private String uuidForAnswerTwo;
//	private String uuidForUser;
//
//
//	@BeforeEach
//	public void Setup() {
//		uuidForQuestion = UUID.randomUUID().toString();
//		uuidForAnswerOne = UUID.randomUUID().toString();
//		uuidForAnswerTwo = UUID.randomUUID().toString();
//		uuidForUser = UUID.randomUUID().toString();
//	}
//
//	@Test
//	public void shouldPostAnswerTest() {
//		final List<Answer> answersList = List.of(
//			new Answer("Answer1", uuidForUser,  10),
//			new Answer("Answer2", uuidForUser, 20)
//		);
//
//		Answers answers = new Answers(answersList, uuidForQuestion);
//		Answers answersRes =  answerService.postAnswer(answers);
//
//		Assertions.assertNotNull(answersRes ,"Successfully Post a Answer");
//	}
//
//	@Test
//	public void shouldFindByQuestionIdTest() {
//
//		Optional<Answers> answersRes = answerService.findByQuestionId(uuidForQuestion);
//
//		Assertions.assertNotNull(answersRes);
//		Assertions.assertTrue(answersRes.isPresent());
//		Assertions.assertEquals("Answer1", answersRes.get().getListOfAnswers().get(0).getContent() );
//		Assertions.assertEquals(10, answersRes.get().getListOfAnswers().get(0).getRating() );
//	}
//
//}
