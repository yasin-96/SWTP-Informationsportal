package de.thm.swtp.information_portal;

/**
 * A Integration test : QuestionController
 */
import java.net.URI;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.QuestionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class QuestionControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private QuestionRepository questionRepository ;

	@BeforeEach
	public void Setup() {
		questionRepository.deleteAll();
	}

	/**
	 * Test method for finding all Questions 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void shouldaFindAllQuestionsIntegrationTest() throws Exception{

		// Save Questions
		Question questionOne = createQuestionList("Header1" , "Content1" , "Tag1" , "Tag2");
		Question questionTwo = createQuestionList("Header2" , "Content3" , "Tag1" , "Tag2");

		questionRepository.save(questionOne);
		questionRepository.save(questionTwo);

		final String baseUrl = "http://localhost:" + port + "/api/allQuestions";
		URI uri = new URI(baseUrl);
		Question[] questions= restTemplate.getForObject(uri, Question[].class);

		Assertions.assertNotNull(questions);	
		Assertions.assertEquals(2 , questions.length);
		Assertions.assertEquals("Content1", questions[0].getContent() );
		Assertions.assertEquals("Header1" , questions[0].getHeader());
	}

	/**
	 * Test method for finding Question by ID 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void shouldFindQuestionIntegrationTest() throws Exception {

		Question CreatedQuestion =  questionRepository.save(createQuestionList("Header2", "Content2" , "Tag1" , "Tag2"));

		final String baseUrl = "http://localhost:" + port + "/api/questionById/" + CreatedQuestion.getId();
		URI uri = new URI(baseUrl);
		Question questionResult = restTemplate.getForObject(uri, Question.class);

		Assertions.assertNotNull(questionResult);	
		Assertions.assertAll("Should be equals : " ,
				() -> Assertions.assertEquals("Content2", questionResult.getContent()),
				() -> Assertions.assertEquals("Header2" , questionResult.getHeader())
				);
	}

	/**
	 * Test method for finding Question by tag 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void shouldfindByTagIntegrationTest() throws Exception {
		
		questionRepository.save(createQuestionList("Header2", "Content2" , "Tag1" , "Tag2"));

		final String baseUrl = "http://localhost:" + port + "/api/questionByTag/Tag1";
		URI uri = new URI(baseUrl);
		Question[] questionResult = restTemplate.getForObject(uri, Question[].class);

		Assertions.assertNotNull(questionResult);
		Assertions.assertEquals(1, questionResult.length);	
		Assertions.assertAll("Should be equals : " ,
				() -> Assertions.assertEquals("Content2", questionResult[0].getContent()),
				() -> Assertions.assertEquals("Header2" , questionResult[0].getHeader())
				);
	}
	
	/**
	 * Testing of PostQuestion method   
	 * @throws Exception
	 */
	@Test
	@Order(3)
	public void shouldPostQuestionIntegrationTest() throws Exception {		
		Question question = createQuestionList("Header4", "Content4" , "Tag3" , "Tag4");

		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/api/newQuestion", question, String.class);
		Assertions.assertEquals(201, responseEntity.getStatusCodeValue());

	}

	/**
	 * 
	 * @return Question with tags
	 */
	private Question createQuestionList( String header , String content , String tagOne , String tagTwo) {
		final Question question = new Question(header, content, Arrays.asList( new Tag(tagOne),new Tag(tagTwo)));
		return  question;
	}
}
