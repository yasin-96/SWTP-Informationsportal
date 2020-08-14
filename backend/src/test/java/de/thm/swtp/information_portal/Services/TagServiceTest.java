package de.thm.swtp.information_portal.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.thm.swtp.information_portal.service.TagService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.service.QuestionService;

@DataMongoTest
@TestMethodOrder(Alphanumeric.class)
class TagServiceTest {

	@MockBean
	private QuestionService questionService;

	@MockBean
	private TagService tagService;

	private String uuidForUser;
	private List<Tag> tags;

	@BeforeEach
	public void Setup() {
		uuidForUser = "UUUUUUUU-0000-USER-0000-000000000001";
		tags = List.of(
				new Tag("Tag1"),
				new Tag("Tag2")
		);
	}

	@Test
	@DisplayName("Save a list of empty Tags in new Question should not worked")
	public void addNewEmptyTagListInQuestionAndSave() {

		final var question = new Question("Header for Tag", "Content for Tag Service", null, uuidForUser);

		Mockito.when(questionService.postQuestion(question)).thenReturn(question);

		var questionRes = questionService.postQuestion(question);
		var allTagsFromQuestion = questionRes.getTags();


		Assertions.assertNotNull(allTagsFromQuestion, "Question has no Tags");
	}


	@Test
	@DisplayName("Can the save a list of Tags in new Question ")
	public void addNewTagsInQuestionAndSave() {

		final var question = new Question("Header for Tag", "Content for Tag Service", tags, uuidForUser);

		Mockito.when(questionService.postQuestion(question)).thenReturn(question);

		var questionRes = questionService.postQuestion(question);
		var allTagsFromQuestion = questionRes.getTags();


		Assertions.assertNotNull(allTagsFromQuestion, "Question has Tags");
		Assertions.assertNotNull(questionRes ,"Successfully post a Question with valid Tags");

	}


	@Test
	@DisplayName("Test if the tags from test before could find")
	public void findNewCreatedTest() {

		Mockito.when(tagService.checkIfTagsExist(tags)).thenReturn(tags);

		var resultFromService =  tagService.checkIfTagsExist(tags);
		Assertions.assertNotNull(resultFromService ,"Successfully find new created tags");
	}







	@Test
	@Order(3)
	public void shouldGetQuestionTest() {

		List<Tag> tags = new ArrayList<>();
		final Tag  tag = new Tag("Tag100");
		tags.add(tag);

		final Question question = new Question("Header1", "Content1", tags, uuidForUser);
		Question CreatedQuestion =  questionService.postQuestion(question);

		Optional<Question> questionRes = questionService.getQuestion(CreatedQuestion.getId());

		Assertions.assertNotNull(questionRes);
		Assertions.assertTrue(questionRes.isPresent());
		Assertions.assertEquals("Content1", questionRes.get().getContent() );
		Assertions.assertEquals("Header1", questionRes.get().getHeader() );
	}

	@Test
	@Order(4)
	public void shouldFindByTagTest() {

		List<Question> questionRes = questionService.findByTag("Tag1");

		Assertions.assertNotNull(questionRes);
		Assertions.assertEquals("Content1", questionRes.get(0).getContent() );
		Assertions.assertEquals("Header1", questionRes.get(0).getHeader() );
	}






}
