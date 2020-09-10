//package de.thm.swtp.information_portal.Integration;
///**
// * A Integration test : QuestionController
// */
//
//import java.util.List;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.web.FilterChainProxy;
//import org.springframework.test.context.ContextConfiguration;
//import de.thm.swtp.information_portal.models.Question.Question;
//import de.thm.swtp.information_portal.models.Tag.Tag;
//import de.thm.swtp.information_portal.repositories.AnswerRepository;
//import de.thm.swtp.information_portal.repositories.QuestionRepository;
//import de.thm.swtp.information_portal.repositories.TagRepository;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//class QuestionControllerIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private FilterChainProxy springSecurityFilterChain;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Autowired
//    private AnswerRepository answerRepository;
//
//    private String testUsername = "Info-Portal@app.de";
//    private String testUserPassword = "Biq3RDY4";
//
//    private String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
//            ".eyJleHAiOjE1OTk1MDI4MDQsImlhdCI6MTU5OTQ5ODgzNCwiYXV0a" +
//            "F90aW1lIjoxNTk5NDk4NDkzLCJqdGkiOiI5MGZhMjRhYi1hZjc5LTQ" +
//            "1NWUtODEzOS04MTljYTZlMGFmZTQiLCJpc3MiOiJodHRwczovL3N3d" +
//            "HAucGN2b2xrbWVyLmRlL2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI" +
//            "6ImFjY291bnQiLCJzdWIiOiIxMWJlZWQ3Ny01YWI2LTQ5NWQtYmFkZ" +
//            "i1lOTFlYzBjODYyN2MiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJnYXRl" +
//            "d2F5Iiwibm9uY2UiOiJHM1FIal9wb0IzNE1NT3VBMXhhQmZHZFBTRWY" +
//            "zR0hNcllhbURpd2JVMDdZIiwic2Vzc2lvbl9zdGF0ZSI6IjE5MGJkNG" +
//            "Q2LWQxYmUtNDZiMC05YmUyLTQ3Y2ZkZDEyMTQ0NSIsImFjciI6IjEiL" +
//            "CJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVy" +
//            "aWZpZWQiOmZhbHNlLCJuYW1lIjoiQWxleGFuZGVyIFRlc3QiLCJwcmV" +
//            "mZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0VVVVIiwiZ2l2ZW5fbmFtZSI6Ik" +
//            "FsZXhhbmRlciIsImZhbWlseV9uYW1lIjoiVGVzdCIsImVtYWlsIjoiY" +
//            "S5qQHRlc3QuY29tIn0.c45k5crRiKOKRTxEXwivP4r0ZMLcEDgvZc_qZq9yh9Q";
//
//
//    @BeforeEach
//    public void Setup() {
//        questionRepository.deleteAll();
//        answerRepository.deleteAll();
//
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .addFilter(springSecurityFilterChain)
//                .build();
//    }
//
//    /**
//     * Test method for finding all Questions
//     * @throws Exception
//     */
//    @Test
//    @Order(1)
//    public void shouldaFindAllQuestionsIntegrationTest() throws Exception {
//
//        // Save Questions
//        var questionOne = new Question(
//                "Header1",
//                "Content1",
//                List.of(
//                        new Tag("Tag1"),
//                        new Tag("Tag2")
//                ),
//                "userOneId",
//                "userOne"
//        );
//
//        var questionTwo = new Question(
//                "Header1",
//                "Content1",
//                List.of(
//                        new Tag("Tag33"),
//                        new Tag("Tag22")
//                ),
//                "userTwoId",
//                "userTwo"
//        );
//
//        questionRepository.save(questionOne);
//        questionRepository.save(questionTwo);
//
//        mockMvc.perform()
//                getClass("/api/allQuestions")
//        )
//
//        //var uri = new URI(baseUrl + "/allQuestions");
//
//        /*var questions = restTemplate
//                .
//                .withBasicAuth("ajjz90", "9731-*/LP!")
//                //.getForEntity("/api/allQuestions", Question.class);*/
//
//        //var questions= restTemplate.getForObject(uri, Question[].class);
//
//        Assertions.assertNotNull(questions);
//        //Assertions.assertEquals(2, questions.getBody().length);
//        //Assertions.assertEquals("Content1", questions[0].getContent() );
//        //Assertions.assertEquals("Header1" , questions[0].getHeader());
//    }
//
//    /**
//     * Test method for finding Question by ID
//     * @throws Exception
//     */
//	/*@Test
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
//    */
//    /**
//     * Test method for finding Question by tag
//     * @throws Exception
//     */
//	/*@Test
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
//	}*/
//
//    /**
//     * Testing of PostQuestion method
//     * @throws Exception
//     */
//	/*@Test
//	@Order(3)
//	public void shouldPostQuestionIntegrationTest() throws Exception {
//		Question question = createQuestionList("Header4", "Content4" , "Tag3" , "Tag4");
//
//		ResponseEntity<String> responseEntity = this.restTemplate
//				.postForEntity("http://localhost:" + port + "/api/newQuestion", question, String.class);
//		Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
//
//	}*/
//
//	/*@Test
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
//		answerRepository.save(
//		        new Answers(
//                    Arrays.asList(
//                            new Answer("Ans1", 12 , "userOneId", "userOne"),
//                            new Answer("Ans2", 22, "userTwoId", "userTwo")
//                    ),
//                    questionOne.getId()
//                )
//        );
//
//		answerRepository.save(
//		        new Answers(
//		                Arrays.asList(
//		                        new Answer("Ans3", 13, "userThreeId", "userThree"),
//                                new Answer("Ans4", 22, "userFiveId", "userFive")
//                        ),
//                        questionTwo.getId()
//                )
//        );
//
//		var uri = new URI(baseUrl + "question/getMostActiveQuestions");
//		Question[] questions = restTemplate.getForObject(uri, Question[].class);
//
//		Assertions.assertNotNull(questions);
//		Assertions.assertEquals(2 , questions.length);
//		Assertions.assertEquals("Content1", questions[0].getContent() );
//		Assertions.assertEquals("Header1" , questions[0].getHeader());
//	}*/
//}
