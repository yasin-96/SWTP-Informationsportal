//package de.thm.swtp.information_portal.Integration;//package de.thm.swtp.information_portal.Integration;
//
//import static org.mockito.Mockito.when;
//
/////**
//// * A Integration test : TagController
//// */
//import java.net.URI;
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//
//import de.thm.swtp.information_portal.models.Answer.Answer;
//import de.thm.swtp.information_portal.models.Answer.Answers;
//import de.thm.swtp.information_portal.models.Question.Question;
//import de.thm.swtp.information_portal.models.Tag.Tag;
//import de.thm.swtp.information_portal.repositories.AnswerRepository;
//import de.thm.swtp.information_portal.repositories.QuestionRepository;
//import de.thm.swtp.information_portal.repositories.TagRepository;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(OrderAnnotation.class)
//@ComponentScan()
//@DataMongoTest
//@ContextConfiguration
//class TagControllerIntegrationTest {
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Autowired
//	private TagRepository tagRepository ;
//
//	@Autowired
//	private QuestionRepository questionRepository;
//
//	@BeforeEach
//	public void Setup() {
//		tagRepository.deleteAll();
//		questionRepository.deleteAll();
//	}
//
//	/**
//	 * Test method for finding all Tags
//	 * @throws Exception
//	 */
//	@Test
//	@Order(1)
//	public void shouldGetAllTagsIntegrationTest() throws Exception{
//
//
//		tagRepository.save(new Tag("Tag1"));
//		tagRepository.save(new Tag("Tag2"));
//		tagRepository.save(new Tag("Tag3"));
//
//		final String baseUrl = "http://localhost:" + port + "/api/getAllTags";
//		URI uri = new URI(baseUrl);
//		Tag[] tags= restTemplate.getForObject(uri, Tag[].class);
//
//		Assertions.assertNotNull(tags);
//		Assertions.assertEquals(3 , tags.length);
//		Assertions.assertEquals("Tag1", tags[0].getName());
//	}
//
//
//	@Test
//	@Order(3)
//	public void shouldGetTagsWithMostQuestionsIntegrationTest() throws Exception{
//
//		tagRepository.save(new Tag("Tag1"));
//		tagRepository.save(new Tag("Tag2"));
//		tagRepository.save(new Tag("Tag3"));
//
//		questionRepository.save(new Question("Header 1", "Content 1", Arrays.asList( new Tag("Tag 1"),new Tag("Tag 2"))));
//		questionRepository.save(new Question("Header 2", "Content 2", Arrays.asList( new Tag("Tag 3"),new Tag("Tag 4"))));
//
//
//		final String baseUrl = "http://localhost:" + port + "/api/tagsWithMostQuestions";
//		URI uri = new URI(baseUrl);
//		Tag[] tags= restTemplate.getForObject(uri, Tag[].class);
//
//		Assertions.assertNotNull(tags);
//		Assertions.assertEquals(0 , tags.length);
//		//Assertions.assertEquals("Tag1", tags[0].getName());
//	}
//
//	/**
//	 * Test method for checkTags
//	 * @throws Exception
//	 */
//	@Test
//	@Order(2)
//	public void shouldCheckTagsIntegrationTest() throws Exception {
//
//		tagRepository.save(new Tag("Tag1"));
//		tagRepository.save(new Tag("Tag2"));
//		tagRepository.save(new Tag("Tag3"));
//
//		final String baseUrl = "http://localhost:" + port + "/api/checkTags/Tag1" ;
//		URI uri = new URI(baseUrl);
//		Tag tag = restTemplate.getForObject(uri, Tag.class);
//
//		Assertions.assertNotNull(tag);
//		Assertions.assertEquals("Tag1", tag.getName());
//	}
//
//}
