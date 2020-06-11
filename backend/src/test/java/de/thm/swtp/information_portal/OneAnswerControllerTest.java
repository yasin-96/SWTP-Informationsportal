package de.thm.swtp.information_portal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import de.thm.swtp.information_portal.models.Answer;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OneAnswerController.class)
class OneAnswerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OneAnswerService oneAnswerService ;

	private final String URL = "/api/";

	/**
	 * Test method for finding OneAnswer by ID 
	 * @throws Exception
	 */
	@Test
	public void shouldGetOneAnswerTest() throws Exception {

		Optional<Answer> optional = Optional.of(new Answer("Answer1", 10));

		when(oneAnswerService.getAnswer(optional.get().getId())).thenReturn(optional);

		mockMvc.perform( MockMvcRequestBuilders
				.get(URL + "getOneAnswer/{id}" , optional.get().getId())
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(optional.get().getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Answer1"));	

	
	}
}
