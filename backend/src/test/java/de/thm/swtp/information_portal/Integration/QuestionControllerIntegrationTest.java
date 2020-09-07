package de.thm.swtp.information_portal.Integration;//package de.thm.swtp.information_portal.Integration;
//
///**
// * A Integration test : QuestionController
// */
//import java.net.URI;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
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
//import org.springframework.web.util.UriComponentsBuilder;
//
//import de.thm.swtp.information_portal.models.Answer.Answer;
//import de.thm.swtp.information_portal.models.Answer.Answers;
//import de.thm.swtp.information_portal.models.Question.Question;
//import de.thm.swtp.information_portal.models.Tag.Tag;
//import de.thm.swtp.information_portal.repositories.AnswerRepository;
//import de.thm.swtp.information_portal.repositories.QuestionRepository;
//import de.thm.swtp.information_portal.repositories.TagRepository;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(OrderAnnotation.class)
//class QuestionControllerIntegrationTest {
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Autowired
//	private QuestionRepository questionRepository ;
//
//	@Autowired
//	private TagRepository tagRepository;
//
//	@Autowired
//	private AnswerRepository answerRepository;
//
//	@BeforeEach
//	public void Setup() {
//		questionRepository.deleteAll();
//		answerRepository.deleteAll();
//	}
//
//	/**
//	 * Test method for finding all Questions
//	 * @throws Exception
//	 */
//	@Test
//	@Order(1)
//	public void shouldaFindAllQuestionsIntegrationTest() throws Exception{
//
//		// Save Questions
//		Question questionOne = createQuestionList("Header1" , "Content1" , "Tag1" , "Tag2");
//		Question questionTwo = createQuestionList("Header2" , "Content3" , "Tag1" , "Tag2");
//
//		questionRepository.save(questionOne);
//		questionRepository.save(questionTwo);
//
//		final String baseUrl = "http://localhost:" + port + "/api/allQuestions";
//		URI uri = new URI(baseUrl);
//		Question[] questions= restTemplate.getForObject(uri, Question[].class);
//
//		Assertions.assertNotNull(questions);
//		Assertions.assertEquals(2 , questions.length);
//		Assertions.assertEquals("Content1", questions[0].getContent() );
//		Assertions.assertEquals("Header1" , questions[0].getHeader());
//	}
//
//	/**
//	 * Test method for finding Question by ID
//	 * @throws Exception
//	 */
//	@Test
//	@Order(2)
//	public void shouldFindQuestionIntegrationTest() throws Exception {
//
//		Question CreatedQuestion =  questionRepository.save(createQuestionList("Header2", "Content2" , "Tag1" , "Tag2"));
//
//		final String baseUrl = "http://localhost:" + port + "/api/questionById/" + CreatedQuestion.getId();
//		URI uri = new URI(baseUrl);
//		Question questionResult = restTemplate.getForObject(uri, Question.class);
//
//		Assertions.assertNotNull(questionResult);
//		Assertions.assertAll("Should be equals : " ,
//				() -> Assertions.assertEquals("Content2", questionResult.getContent()),
//				() -> Assertions.assertEquals("Header2" , questionResult.getHeader())
//				);
//	}
//
//	/**
//	 * Test method for finding Question by tag
//	 * @throws Exception
//	 */
//	@Test
//	@Order(2)
//	public void shouldfindByTagIntegrationTest() throws Exception {
//
//		questionRepository.save(createQuestionList("Header2", "Content2" , "Tag1" , "Tag2"));
//		tagRepository.save(new Tag("Tag1"));
//
//		final String baseUrl = "http://localhost:" + port + "/api/questionByTag/Tag1";
//		URI uri = new URI(baseUrl);
//		Question[] questionResult = restTemplate.getForObject(uri, Question[].class);
//
//		Assertions.assertNotNull(questionResult);
//		Assertions.assertEquals(1, questionResult.length);
//		Assertions.assertAll("Should be equals : " ,
//				() -> Assertions.assertEquals("Content2", questionResult[0].getContent()),
//				() -> Assertions.assertEquals("Header2" , questionResult[0].getHeader())
//				);
//	}
//
//	/**
//	 * Testing of PostQuestion method
//	 * @throws Exception
//	 */
//	@Test
//	@Order(3)
//	public void shouldPostQuestionIntegrationTest() throws Exception {
//		Question question = createQuestionList("Header4", "Content4" , "Tag3" , "Tag4");
//
//		ResponseEntity<String> responseEntity = this.restTemplate
//				.postForEntity("http://localhost:" + port + "/api/newQuestion", question, String.class);
//		Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
//
//	}
//
//	@Test
//	@Order(4)
//	public void shouldEditQuestionIntegrationTest() throws Exception {
//		Question questionToBeEdited = createQuestionList("Header4", "Content4" , "Tag3" , "Tag4");
//
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<Question> entity = new HttpEntity<Question>(questionToBeEdited, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/question",
//				HttpMethod.PUT, entity, String.class);
//
//		Assertions.assertEquals(201, response.getStatusCodeValue());
//
//	}
//
//	@Test
//	public void shouldGetDataByQueryTest() throws Exception {
//
//		questionRepository.save(createQuestionList("Header1" , "Content1" , "Tag1" , "Tag2"));
//		questionRepository.save(createQuestionList("Header2" , "Content2" , "Tag1" , "Tag2"));
//
//
//		Map<String, String>  map= new HashMap<>();
//		map.put("searchQuery", "Tag1");
//
//		URI targetUrl= UriComponentsBuilder.fromUriString("http://localhost:" + port)   // Build the base link
//			    .path("/api/question/query")                            // Add path
//			    .queryParam("searchQuery", "Tag1")                                // Add one or more query params
//			    .build()                                              // Build the URL
//			    .encode()                                                // Encode any URI items that need to be encoded
//			    .toUri();
//
//		Question[] questionResult = restTemplate.getForObject(targetUrl, Question[].class);
//
//		Assertions.assertNotNull(questionResult);
//		Assertions.assertEquals(2, questionResult.length);
//		Assertions.assertAll("Should be equals : " ,
//				() -> Assertions.assertEquals("Content1", questionResult[0].getContent()),
//				() -> Assertions.assertEquals("Header1" , questionResult[0].getHeader())
//				);
//	}
//
//	@Test
//	public void shouldGetMostActiveQuestionsIntegrationTest() throws Exception{
//
//		// Save Questions
//		Question questionOne = createQuestionList("Header1" , "Content1" , "Tag1" , "Tag2");
//		Question questionTwo = createQuestionList("Header2" , "Content3" , "Tag1" , "Tag2");
//
//		questionRepository.save(questionOne);
//		questionRepository.save(questionTwo);
//
//		answerRepository.save(new Answers(Arrays.asList(new Answer("Ans1", 12), new Answer("Ans2", 22) ),questionOne.getId()));
//		answerRepository.save(new Answers(Arrays.asList(new Answer("Ans3", 13), new Answer("Ans4", 22) ),questionTwo.getId()));
//
//		final String baseUrl = "http://localhost:" + port + "/api/question/getMostActiveQuestions";
//		URI uri = new URI(baseUrl);
//		Question[] questions = restTemplate.getForObject(uri, Question[].class);
//
//		Assertions.assertNotNull(questions);
//		Assertions.assertEquals(2 , questions.length);
//		Assertions.assertEquals("Content1", questions[0].getContent() );
//		Assertions.assertEquals("Header1" , questions[0].getHeader());
//	}
//
//	/**
//	 *
//	 * @return Question with tags
//	 */
//	private Question createQuestionList( String header , String content , String tagOne , String tagTwo) {
//		final Question question = new Question(header, content, Arrays.asList( new Tag(tagOne),new Tag(tagTwo)));
//		return  question;
//	}
//}
