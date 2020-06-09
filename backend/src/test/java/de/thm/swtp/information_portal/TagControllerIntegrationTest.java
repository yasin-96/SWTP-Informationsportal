package de.thm.swtp.information_portal;

/**
 * A Integration test : TagController
 */
import java.net.URI;

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

import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class TagControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TagRepository tagRepository ;

	@BeforeEach
	public void Setup() {
		tagRepository.deleteAll();
	}

	/**
	 * Test method for finding all Tags 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void shouldGetAllTagsIntegrationTest() throws Exception{

		
		tagRepository.save(new Tag("Tag1"));
		tagRepository.save(new Tag("Tag2"));
		tagRepository.save(new Tag("Tag3"));

		final String baseUrl = "http://localhost:" + port + "/api/getAllTags";
		URI uri = new URI(baseUrl);
		Tag[] tags= restTemplate.getForObject(uri, Tag[].class);

		Assertions.assertNotNull(tags);	
		Assertions.assertEquals(3 , tags.length);
		Assertions.assertEquals("Tag1", tags[0].getName());
	}

	/**
	 * Test method for checkTags 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void shouldCheckTagsIntegrationTest() throws Exception {

		tagRepository.save(new Tag("Tag1"));
		tagRepository.save(new Tag("Tag2"));
		tagRepository.save(new Tag("Tag3"));
		
		final String baseUrl = "http://localhost:" + port + "/api/checkTags/Tag1" ;
		URI uri = new URI(baseUrl);
		Tag tag = restTemplate.getForObject(uri, Tag.class);

		Assertions.assertNotNull(tag);	
		Assertions.assertEquals("Tag1", tag.getName());
	}

}
