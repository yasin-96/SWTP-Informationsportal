//package de.thm.swtp.information_portal.Integration;
//
///**
// * A Integration test : CommentController
// */
//import java.net.URI;
//import java.util.Arrays;
//import java.util.List;
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
//import org.springframework.http.ResponseEntity;
//
//import de.thm.swtp.information_portal.models.Comment.Comment;
//import de.thm.swtp.information_portal.models.Comment.Comments;
//import de.thm.swtp.information_portal.repositories.CommentRepository;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(OrderAnnotation.class)
//class CommentControllerIntegrationTest {
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@Autowired
//	private CommentRepository commentRepository;
//
//	@BeforeEach
//	public void Setup() {
//		commentRepository.deleteAll();
//	}
//
//	/**
//	 * Test method for finding all comments
//	 * @throws Exception
//	 */
//	@Test
//	@Order(1)
//	public void shouldFindAllCommentsIntegrationTest() throws Exception{
//
//		// Save Comments
//		List<Comment> commentsList = createCommentsList();
//		commentRepository.save(new Comments(commentsList, "1S"));
//
//		final String baseUrl = "http://localhost:" + port + "/api/allComments";
//		URI uri = new URI(baseUrl);
//		Comments[] comments = restTemplate.getForObject(uri, Comments[].class);
//
//		Assertions.assertNotNull(comments);
//		Assertions.assertEquals(1 , comments.length);
//		Assertions.assertEquals("Comment1", comments[0].getComments().get(0).getContent() );
//		Assertions.assertEquals("1S" , comments[0].getId());
//	}
//
//	/**
//	 * Test method for finding answers by ID
//	 * @throws Exception
//	 */
//	@Test
//	@Order(2)
//	public void shouldFindByAnswerIdIntegrationTest() throws Exception {
//
//		// Save Comments
//		List<Comment> commentsList = createCommentsList();
//		commentRepository.save(new Comments(commentsList, "1S"));
//
//		final String baseUrl = "http://localhost:" + port + "/api/commentsByAnswerId/1S";
//		URI uri = new URI(baseUrl);
//		Comments comments = restTemplate.getForObject(uri, Comments.class);
//
//		Assertions.assertNotNull(comments);
//		Assertions.assertEquals("Comment1", comments.getComments().get(0).getContent());
//		Assertions.assertEquals("1S" , comments.getId());
//	}
//
//	/**
//	 * Testing of PostComments method
//	 * @throws Exception
//	 */
//	@Test
//	@Order(3)
//	public void shouldPostCommentsIntegrationTest() throws Exception {
//		Comments comments= new Comments(
//						Arrays.asList(new Comment("Comment3", "USER3" , 10),
//									  new Comment("Comment4", "USER4" , 20)), "2S");
//
//		ResponseEntity<String> responseEntity = this.restTemplate
//				.postForEntity("http://localhost:" + port + "/api/newComments", comments , String.class);
//		Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
//	}
//
//	@Test
//	public void shouldincreaseCommentRatingIntegrationTest() throws Exception {
//
//		Comments comments= new Comments(
//				Arrays.asList(new Comment("Comment3", "USER3" , 10),
//							  new Comment("Comment4", "USER4" , 20)), "2S");
//
//		commentRepository.save(comments);
//
//		ResponseEntity<String> responseEntity = this.restTemplate
//				.postForEntity("http://localhost:" + port + "/api/comment/increaseRating", comments, String.class);
//		Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
//
//	}
//
//	/**
//	 *
//	 * @return list of comment
//	 */
//	private List<Comment> createCommentsList() {
//		return Arrays.asList(
//				new Comment("Comment1", "USER1" , 10),
//				new Comment("Comment2", "USER2" , 20)
//				);
//	}
//}
