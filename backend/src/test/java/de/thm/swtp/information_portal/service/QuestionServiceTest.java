//package de.thm.swtp.information_portal.Services;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.context.annotation.ComponentScan;
//
//import de.thm.swtp.information_portal.models.Question.Question;
//import de.thm.swtp.information_portal.models.Tag.Tag;
//import de.thm.swtp.information_portal.service.QuestionService;
//
//@ComponentScan(basePackages={"de.thm.swtp.information_portal"})
//@DataMongoTest
//@TestMethodOrder(OrderAnnotation.class)
//class QuestionServiceTest {
//
//	@Autowired
//	private QuestionService questionService;
//
//	@Test
//	@Order(1)
//	public void shouldPostQuestionTest() {
//
//		List<Tag> tags = new ArrayList<>();
//		final Tag  tagOne = new Tag("Tag1");
//		final Tag  tagTwo = new Tag("Tag2");
//
//		tags.add(tagOne);
//		tags.add(tagTwo);
//
//		final Question question = new Question("Header1", "Content1", tags);
//
//		Question questionRes =  questionService.postQuestion(question);
//
//		Assertions.assertNotNull(questionRes ,"Successfully Post a Question");
//
//	}
//
//	@Test
//	@Order(2)
//	public void shouldGetAllQuestionsTest() {
//
//		List<Question> questionRes =  questionService.getAllQuestions();
//
//		Assertions.assertNotNull(questionRes ,"Successfully find all questions");
//		Assertions.assertTrue(!questionRes.isEmpty());
//	}
//
//	@Test
//	@Order(3)
//	public void shouldGetQuestionTest() {
//
//		List<Tag> tags = new ArrayList<>();
//		final Tag  tag = new Tag("Tag100");
//		tags.add(tag);
//
//		final Question question = new Question("Header1", "Content1", tags);
//		Question CreatedQuestion =  questionService.postQuestion(question);
//
//		Optional<Question> questionRes = questionService.getQuestion(CreatedQuestion.getId());
//
//		Assertions.assertNotNull(questionRes);
//		Assertions.assertTrue(questionRes.isPresent());
//		Assertions.assertEquals("Content1", questionRes.get().getContent() );
//		Assertions.assertEquals("Header1", questionRes.get().getHeader() );
//	}
//
//	@Test
//	@Order(4)
//	public void shouldFindByTagTest() {
//
//		List<Question> questionRes = questionService.findByTag("Tag1");
//
//		Assertions.assertNotNull(questionRes);
//		Assertions.assertEquals("Content1", questionRes.get(0).getContent() );
//		Assertions.assertEquals("Header1", questionRes.get(0).getHeader() );
//	}
//
//}
