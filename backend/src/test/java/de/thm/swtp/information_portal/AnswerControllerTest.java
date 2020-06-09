package de.thm.swtp.information_portal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
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

import de.thm.swtp.information_portal.controller.AnswerController;
import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.service.AnswerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AnswerController.class)
class AnswerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AnswerService answerService ;


	private final String URL = "/api/";

	/**
	 * Test method for finding answers by ID 
	 * @throws Exception
	 */
	@Test
	public void shouldFindAnswersTest() throws Exception {

		Optional<Answers> optional = Optional.of(new Answers(createAnswersList(), "Q1"));

		when(answerService.findByQuestionId("Q1")).thenReturn(optional);

		mockMvc.perform( MockMvcRequestBuilders
				.get(URL + "answersByQuestionId/{id}" , "Q1")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value("Q1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer1"));		
	}

	/**
	 * Testing of PostAnswers method   
	 * @throws Exception
	 */
	@Test
	public void shouldPostAnswerTest() throws Exception {		
		Answers answers = new Answers(
				Arrays.asList(new Answer("Answer3", 10) ,
						new Answer("Answer4", 20) ) , "Q2");

		mockMvc.perform( MockMvcRequestBuilders
				.post(URL + "answer")
				.content(asJsonString(answers))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value("Q2"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer3"));	

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
	 * @return list of Answers
	 */
	private List<Answer> createAnswersList() {
		return Arrays.asList(
				new Answer("Answer1", 10),
				new Answer("Answer2", 20)
				);
	}
}
