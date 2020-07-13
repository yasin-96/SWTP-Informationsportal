package de.thm.swtp.information_portal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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

		MvcResult mvcResult = mockMvc.perform(
				get(URL + "answersByQuestionId/{id}" , "Q1")
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
		
		mockMvc.perform(asyncDispatch(mvcResult))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value("Q1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer1"));		
	}
	
	@Test
	public void shouldIncreaseAnswerRatingTest() throws Exception {

		Optional<Answers> optional = Optional.of(new Answers(createAnswersList(), "Q1"));
		when(answerService.findByQuestionId("Q1")).thenReturn(optional);

		MvcResult mvcResult = mockMvc.perform(
				post(URL + "/answer/increaseRating")
				.content(asJsonString(optional.get()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
		
		mockMvc.perform(asyncDispatch(mvcResult))
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
				Arrays.asList( new Answer("Answer3", 10) ,
							   new Answer("Answer4", 20) ) , "Q2");

		MvcResult mvcResult = mockMvc.perform(
				 post(URL + "answer")
				.content(asJsonString(answers))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
		
		mockMvc.perform(asyncDispatch(mvcResult))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value("Q2"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer3"));	

	}
	
	@Test
	public void shouldGetAnswerToBeEditedTest() throws Exception {
		Optional<Answers> answersOne = Optional.of(new Answers(createAnswersList(), "Q1"));
		when(answerService.findByQuestionId("Q1")).thenReturn(answersOne);

		String[] ids = {answersOne.get().getId() , answersOne.get().getListOfAnswers().get(1).getId()};
		
		MvcResult mvcResult = mockMvc.perform(
				get(URL + "answer/answerTobeEdited")
				.content(asJsonString(ids))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
		
		mockMvc.perform(asyncDispatch(mvcResult))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Answer2"))	
		.andExpect(MockMvcResultMatchers.jsonPath("$.rating").value("20"));		
	}

	
	@Test
	public void shouldEditAnswerTest() throws Exception {
		
		Optional<Answers> answers = Optional.of(new Answers(createAnswersList(), "Q1"));
		when(answerService.findByQuestionId("Q1")).thenReturn(answers);
		
		List<Answer> answersList = answers.get().getListOfAnswers();
		answersList.get(0).setContent("new Content");
		answersList.get(0).setRating(120);
		answers.get().setListOfAnswers(answersList);		
		
		MvcResult mvcResult = mockMvc.perform(
				put(URL + "answer/{id}" , answers.get().getId())
				.content(asJsonString(answers.get()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
               .andExpect(request().asyncStarted())
               .andDo(MockMvcResultHandlers.log())
               .andReturn();
		
		mockMvc.perform(asyncDispatch(mvcResult))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value("Q1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("new Content"));	
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