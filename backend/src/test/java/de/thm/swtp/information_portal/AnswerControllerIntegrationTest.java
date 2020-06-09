package de.thm.swtp.information_portal;

/**
 * A Integration test  : AnswerController
 * Integration tests for Spring rest controller apis using @SpringBootTest and Junit5.
 */
import java.net.URI;
import java.util.Arrays;
import java.util.List;

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

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.repositories.AnswerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class AnswerControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AnswerRepository answerRepository ;

	@BeforeEach
	public void Setup() {
		answerRepository.deleteAll();
	}

	/**
	 * Test method for finding answers by Question ID 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void shouldFindAnswersIntegrationTest() throws Exception {

		// Save Answers
		List<Answer> answersList = createAnswersList();
		answerRepository.save(new Answers(answersList, "Q1"));

		final String baseUrl = "http://localhost:" + port + "/api/answersByQuestionId/Q1";
		URI uri = new URI(baseUrl);
		Answers answers = restTemplate.getForObject(uri, Answers.class);

		Assertions.assertNotNull(answers);	
		Assertions.assertAll("Should be equals : " ,
				() -> Assertions.assertEquals("Answer1", answers.getListOfAnswers().get(0).getContent()),
				() -> Assertions.assertEquals("Q1" , answers.getId())
				);
	}

	/**
	 * Testing of PostAnswer method   
	 * @throws Exception
	 */
	@Test
	@Order(3)
	public void shouldPostAnswerIntegrationTest() throws Exception {		

		Answers answers = new Answers(
				Arrays.asList(new Answer("Answer3", 10) ,
							  new Answer("Answer4", 20) ) , "Q2");
		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/api/answer", answers, String.class);
		Assertions.assertEquals(201, responseEntity.getStatusCodeValue());

	}

	/**
	 * Create answers list
	 * @return list of Answers
	 */
	private List<Answer> createAnswersList() {
		return Arrays.asList(
				new Answer("Answer1", 10),
				new Answer("Answer2", 20)
				);
	}
}
