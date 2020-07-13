package de.thm.swtp.information_portal;

/**
 * A Integration test : OneAnswerController
 */
import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.repositories.OneAnswerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
class OneAnswerControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private OneAnswerRepository oneAnswerRepository ;

	/**
	 * Test method for finding OneAnswer by ID 
	 * @throws Exception
	 */
	@Test
	public void shouldGetOneAnswerIntegrationTest() throws Exception {

		final Answer answerOne = new Answer("Answer1", 10);	
		oneAnswerRepository.save(answerOne);
		
		Optional<Answer> answersResult = oneAnswerRepository.findById(answerOne.getId());
		final String baseUrl = "http://localhost:" + port + "/api/getOneAnswer/" + answersResult.get().getId();
		URI uri = new URI(baseUrl);
		Answer answer = restTemplate.getForObject(uri, Answer.class);

		Assertions.assertNotNull(answer);	
		Assertions.assertAll("Should be equals : " ,
				() -> Assertions.assertEquals("Answer1", answer.getContent()),
				() -> Assertions.assertEquals(10 , answer.getRating())
				);
	}
}
