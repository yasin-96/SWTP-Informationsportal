package de.thm.swtp.information_portal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.thm.swtp.information_portal.controller.QuestionController;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.service.QuestionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuestionController.class)
@TestMethodOrder(OrderAnnotation.class)
class QuestionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionService questionService ;

	private final String URL = "/api/";

	/**
	 * Test method for finding all Questions 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void shouldaFindAllQuestionsTest() throws Exception{

		when(questionService.getAllQuestions()).thenReturn(
				Arrays.asList(createQuestionList("Header1" , "Content1" , "Tag1" , "Tag2"), 
						createQuestionList("Header2" , "Content2" , "Tag1" , "Tag2"))
				);

		mockMvc.perform( MockMvcRequestBuilders
				.get(URL + "allQuestions")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].header").value("Header1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].content").value("Content1"))
		.andExpect(MockMvcResultMatchers.jsonPath("[0].tags").isArray());		
	}

	/**
	 * Test method for finding Question by ID 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void shouldFindQuestionTest() throws Exception {
		Question createdQuestion = createQuestionList("Header2", "Content2" , "Tag1" , "Tag2");
		when(questionService.getQuestion(createdQuestion.getId())).thenReturn(Optional.of(createdQuestion));

		mockMvc.perform( MockMvcRequestBuilders
				.get(URL + "questionById/{id}" , createdQuestion.getId())
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(createdQuestion.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.header").value("Header2"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Content2"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray());
	}

	/**
	 * Test method for finding Question by tag 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void shouldfindByTagTest() throws Exception {

		when(questionService.findByTag("Tag1")).thenReturn(
				Arrays.asList(createQuestionList("Header1" , "Content1" , "Tag1" , "Tag2"), 
						createQuestionList("Header2" , "Content2" , "Tag1" , "Tag2"))
				);

		mockMvc.perform( MockMvcRequestBuilders
				.get(URL + "/questionByTag/{tag}" , "Tag1")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].header").value("Header1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].content").value("Content1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].tags").isArray());
	}

	/**
	 * Testing of PostQuestion method   
	 * @throws Exception
	 */
	@Test
	@Order(3)
	public void shouldPostQuestionTest() throws Exception {		
		Question question = createQuestionList("Header4", "Content4" , "Tag3" , "Tag4");
		when(questionService.postQuestion(question)).thenReturn(question);

		mockMvc.perform( MockMvcRequestBuilders
				.post(URL + "newQuestion")
				.content(asJsonString(question))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.header").value("Header4"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Content4"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray());

	}

	/**
	 * Convert object to Json
	 * @param obj
	 * @return a value as String 
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
