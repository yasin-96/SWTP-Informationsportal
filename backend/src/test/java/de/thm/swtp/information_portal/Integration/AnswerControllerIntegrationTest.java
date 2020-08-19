//package de.thm.swtp.information_portal.Integration;
//
///**
// * A Integration test  : AnswerController
// * Integration tests for Spring rest controller apis using @SpringBootTest and Junit5.
// */
//import java.net.URI;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpClientErrorException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import de.thm.swtp.information_portal.models.Answer;
//import de.thm.swtp.information_portal.models.Answers;
//import de.thm.swtp.information_portal.repositories.AnswerRepository;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(OrderAnnotation.class)
//class AnswerControllerIntegrationTest {
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Autowired
//	private AnswerRepository answerRepository ;
//
//	@BeforeEach
//	public void Setup() {
//		answerRepository.deleteAll();
//	}
//
//	/**
//	 * Test method for finding answers by Question ID
//	 * @throws Exception
//	 */
//	@Test
//	public void shouldFindAnswersIntegrationTest() throws Exception {
//
//		// Save Answers
//		List<Answer> answersList = createAnswersList();
//		answerRepository.save(new Answers(answersList, "Q1"));
//
//		final String baseUrl = "http://localhost:" + port + "/api/answersByQuestionId/Q1";
//		URI uri = new URI(baseUrl);
//		Answers answers = restTemplate.getForObject(uri, Answers.class);
//
//		Assertions.assertNotNull(answers);
//		Assertions.assertAll("Should be equals : " ,
//				() -> Assertions.assertEquals("Answer1", answers.getListOfAnswers().get(0).getContent()),
//				() -> Assertions.assertEquals("Q1" , answers.getId())
//				);
//	}
//
//	/**
//	 * Testing of PostAnswer method
//	 * @throws Exception
//	 */
//	@Test
//	public void shouldPostAnswerIntegrationTest() throws Exception {
//
//		Answers answers = new Answers(
//				Arrays.asList(new Answer("Answer3", 10) ,
//						new Answer("Answer4", 20) ) , "Q2");
//		ResponseEntity<String> responseEntity = this.restTemplate
//				.postForEntity("http://localhost:" + port + "/api/answer", answers, String.class);
//		Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
//
//	}
//
//	@Test
//	public void shouldEditAnswerIntegrationTest() throws Exception {
//
//		Answers answers = new Answers(
//				Arrays.asList(new Answer("Answer3", 10) ,
//						new Answer("Answer4", 20) ) , "Q2");
//
//		answerRepository.save(answers);
//		answerRepository.findById(answers.getId());
//
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<Answers> entity = new HttpEntity<Answers>(answers, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/answer/Q2",
//				HttpMethod.PUT, entity, String.class);
//
//		Assertions.assertEquals(201, response.getStatusCodeValue());
//
//	}
//
//	@Test
//	public void shouldGetAnswerToBeEditedTest() throws Exception {
//
//		// Save Answers
//		List<Answer> answersList = createAnswersList();
//		Answers answers = new Answers(answersList, "Q1");
//		answerRepository.save(answers);
//
//		String[] ids = new String[2] ;
//		ids[0] = answers.getId();
//		ids[1] = answers.getListOfAnswers().get(1).getId();
//
//		final String baseUrl = "http://localhost:" + port + "/api/answer/answerTobeEdited";
//		URI uri = new URI(baseUrl);
//
//
//		// Create the request body as a MultiValueMap
//		MultiValueMap<String, String[]> body = new LinkedMultiValueMap<String, String[]>();
//		body.add("ids", ids);
//
//		HttpHeaders headers = new HttpHeaders();
//		// Note the body object as first parameter!
//		HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);
//
//		try
//		{
//			restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Answer.class);
//		}
//		catch(HttpClientErrorException ex)
//		{
//			//Verify bad request and missing header
//			Assertions.assertEquals(400, ex.getRawStatusCode());
//			Assertions.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
//		}
//	}
//
//	@Test
//	public void shouldIncreaseAnswerRatingIntegrationTest() throws Exception {
//
//		Answers answers = new Answers(
//				Arrays.asList(new Answer("Answer3", 10) ,
//						new Answer("Answer4", 20) ) , "Q2");
//
//		answerRepository.save(answers);
//
//		ResponseEntity<String> responseEntity = this.restTemplate
//				.postForEntity("http://localhost:" + port + "/api/answer/increaseRating", answers, String.class);
//		Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
//
//	}
//
//	/**
//	 * Create answers list
//	 * @return list of Answers
//	 */
//	private List<Answer> createAnswersList() {
//		return Arrays.asList(
//				new Answer("Answer1", 10),
//				new Answer("Answer2", 20)
//				);
//	}
//
//	/**
//	 * Convert object to Json
//	 * @param obj
//	 * @return a value as String
//	 */
//	public static String asJsonString(final Object obj) {
//		try {
//			return new ObjectMapper().writeValueAsString(obj);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//}
