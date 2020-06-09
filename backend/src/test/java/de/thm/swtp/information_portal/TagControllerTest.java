package de.thm.swtp.information_portal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Order;
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

import de.thm.swtp.information_portal.controller.TagController;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;
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
	
	private final String URL = "/api/";

	/**
	 * Test method for finding all Tags 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void shouldGetAllTagsTest() throws Exception{


		when(tagService.getAllTags()).thenReturn(
				Arrays.asList(new Tag("Tag1") , 
						new Tag("Tag2"),
						new Tag("Tag3")
						));

		mockMvc.perform( MockMvcRequestBuilders
				.get(URL + "getAllTags")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Tag1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Tag2"));
	}

	/**
	 * Test method for checkTags 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void shouldCheckTagsTest() throws Exception {

		when(tagRepository.findByName("Tag1")).thenReturn(new Tag("Tag1"));

		mockMvc.perform( MockMvcRequestBuilders
				.get(URL + "checkTags/{tagsToBeChecked}" , "Tag1")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("name").value("Tag1"));
	}

}
