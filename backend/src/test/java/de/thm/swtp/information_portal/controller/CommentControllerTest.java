package de.thm.swtp.information_portal.controller;//package de.thm.swtp.information_portal.Controller;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import de.thm.swtp.information_portal.controller.CommentController;
//import de.thm.swtp.information_portal.models.Comment.Comment;
//import de.thm.swtp.information_portal.models.Comment.Comments;
//import de.thm.swtp.information_portal.service.CommentService;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(CommentController.class)
//class CommentControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private CommentService commentService ;
//
//
//	private final String URL = "/api/";
//
//	/**
//	 * Test method for finding all comments
//	 * @throws Exception
//	 */
//	@Test
//	public void shouldFindAllCommentsTest() throws Exception {
//
//		when(commentService.findAllComments()).thenReturn(
//				Arrays.asList(new Comments(createCommentsList(), "1S") ,
//						new Comments(createCommentsList(), "2S")
//						));
//
//		MvcResult mvcResult = mockMvc.perform(
//				get(URL + "allComments")
//				.accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//		mockMvc.perform(asyncDispatch(mvcResult))
//		.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("[0].comments[0].content").value("Comment1"))
//		.andExpect(MockMvcResultMatchers.jsonPath("[0].comments[0].userName").value("USER1"))
//		.andExpect(MockMvcResultMatchers.jsonPath("[0].comments").isArray());
//	}
//
//	/**
//	 * Test method for finding answers by ID
//	 * @throws Exception
//	 */
//	@Test
//	public void shouldFindByAnswerIdTest() throws Exception {
//
//		Optional<Comments> optional = Optional.of(new Comments(createCommentsList(), "1S"));
//		when(commentService.findByAnswerId("1S")).thenReturn(optional);
//
//		MvcResult mvcResult = mockMvc.perform(
//				get(URL + "commentsByAnswerId/{id}" , "1S")
//				.accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//		mockMvc.perform(asyncDispatch(mvcResult))
//		.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("comments").exists())
//		.andExpect(MockMvcResultMatchers.jsonPath("id").value("1S"))
//		.andExpect(MockMvcResultMatchers.jsonPath("$.comments.[0].content").value("Comment1"));
//	}
//
//	@Test
//	public void shouldIncreaseCommentRatingTest() throws Exception {
//
//		Optional<Comments> optional = Optional.of(new Comments(createCommentsList(), "1S"));
//		when(commentService.findByAnswerId("1S")).thenReturn(optional);
//
//		MvcResult mvcResult = mockMvc.perform(
//				post(URL + "/comment/increaseRating")
//				.content(asJsonString(optional.get()))
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//		mockMvc.perform(asyncDispatch(mvcResult))
//		.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("comments").exists())
//		.andExpect(MockMvcResultMatchers.jsonPath("id").value("1S"))
//		.andExpect(MockMvcResultMatchers.jsonPath("$.comments.[0].content").value("Comment1"));
//	}
//
//	/**
//	 * Testing of PostComments method
//	 * @throws Exception
//	 */
//	@Test
//	public void shouldPostCommentsTest() throws Exception {
//		Comments comments= new Comments(
//				Arrays.asList(new Comment("Comment3", "USER3" , 10),
//						new Comment("Comment4", "USER4" , 20)), "2S");
//
//		MvcResult mvcResult = mockMvc.perform(
//				 post(URL + "newComments")
//				.content(asJsonString(comments))
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//               .andExpect(request().asyncStarted())
//               .andDo(MockMvcResultHandlers.log())
//               .andReturn();
//
//		mockMvc.perform(asyncDispatch(mvcResult))
//		.andDo(print())
//		.andExpect(status().isCreated())
//		.andExpect(MockMvcResultMatchers.jsonPath("comments").exists())
//		.andExpect(MockMvcResultMatchers.jsonPath("id").value("2S"))
//		.andExpect(MockMvcResultMatchers.jsonPath("$.comments.[0].content").value("Comment3"));
//
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
