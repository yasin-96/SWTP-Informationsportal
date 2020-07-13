package de.thm.swtp.information_portal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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

import de.thm.swtp.information_portal.controller.TagController;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;
import de.thm.swtp.information_portal.service.QuestionService;
import de.thm.swtp.information_portal.service.TagService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TagController.class)
class TagControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TagService tagService ;

	@MockBean
	private TagRepository tagRepository;

	@MockBean
	private QuestionService questionService;

	private final String URL = "/api/";

	/**
	 * Test method for finding all Tags 
	 * @throws Exception
	 */
	@Test
	public void shouldGetAllTagsTest() throws Exception{


		when(tagService.getAllTags()).thenReturn(
				Arrays.asList(new Tag("Tag1") , 
						new Tag("Tag2"),
						new Tag("Tag3")
						));

		MvcResult mvcResult = mockMvc.perform(
				get(URL + "getAllTags")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(request().asyncStarted())
				.andDo(MockMvcResultHandlers.log())
				.andReturn();


		mockMvc.perform(asyncDispatch(mvcResult))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Tag1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Tag2"));
	}

	@Test
	public void shouldGetTagsWithMostQuestionsTest() throws Exception{

		when(questionService.getAllQuestions()).thenReturn(
				Arrays.asList(new Question("Header 1", "Content 1", Arrays.asList( new Tag("Tag 1"),new Tag("Tag 2"))), 
						new Question("Header 2", "Content 2", Arrays.asList( new Tag("Tag 3"),new Tag("Tag 4"))
								)));

		when(tagService.getAllTags()).thenReturn(
				Arrays.asList(new Tag("Tag1") , 
						new Tag("Tag2"),
						new Tag("Tag3")
						));


		MvcResult mvcResult = mockMvc.perform(
				get(URL + "tagsWithMostQuestions")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(request().asyncStarted())
				.andDo(MockMvcResultHandlers.log())
				.andReturn();


		mockMvc.perform(asyncDispatch(mvcResult))
		.andDo(print())
		.andExpect(status().isOk());
		//.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Tag1"))
		//.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Tag2"));
	}

	/**
	 * Test method for checkTags 
	 * @throws Exception
	 */
	@Test
	public void shouldCheckTagsTest() throws Exception {

		when(tagRepository.findByName("Tag1")).thenReturn(new Tag("Tag1"));


		MvcResult mvcResult = mockMvc.perform(
				get(URL + "checkTags/{tagsToBeChecked}" , "Tag1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(request().asyncStarted())
				.andDo(MockMvcResultHandlers.log())
				.andReturn();

		mockMvc.perform(asyncDispatch(mvcResult))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("name").value("Tag1"));
	}

}
