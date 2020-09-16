package de.thm.swtp.information_portal.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.Tag.Tag;

@DataMongoTest
class QuestionRepositoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionRepositoryTest.class);

	@Autowired
	private QuestionRepository   questionRepository;

	@BeforeEach
	public void setUp() {
		 questionRepository.deleteAll();
	}

	@Test
	public void shouldInsertQuestionTest() {

		LOGGER.info("\n\n **********Start insertQuestion Test********** \n\n");

        Question questionOne = new Question(
                "Header1",
                "Content1",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER1",
                "USER1"
        );
        
        Question questionTwo = new Question(
                "Header2",
                "Content2",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER2",
                "USER2"
        );
		
		Question questionResOne = questionRepository.save(questionOne);
		Question questionResTwo = questionRepository.save(questionTwo);
		
		Assertions.assertEquals(questionOne.getId(), questionResOne.getId(), "Ids should be equals");
		Assertions.assertEquals(questionTwo.getId(), questionResTwo.getId(), "Ids should be equals");

		Assertions.assertNotNull(questionResOne , "questionOne should be not null");
		Assertions.assertNotNull(questionResTwo , "questionTwo should be not null");

		Assertions.assertEquals("Content1", questionResOne.getContent());
		Assertions.assertEquals("Content2", questionResTwo.getContent());

		LOGGER.info("\n\n **********End insertQuestion Test********** \n\n");
	}


	@Test
	public void shouldGetQuestionByIdTest() {

		LOGGER.info("\n\n **********Start getQuestionById Test********** \n\n");

		Question question = new Question(
                "Header1",
                "Content1",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER1",
                "USER1"
        );
        

		Question questionRes = questionRepository.save(question);

		Assertions.assertEquals("Header1" , questionRepository.findById(questionRes.getId()).get().getHeader(),
				"Successfully fetched a question by ID");

		LOGGER.info("\n\n **********End getQuestionById Test********** \n\n");
	}

	@Test
	public void shouldUpdateQuestionTest() {

		LOGGER.info("\n\n **********Start updateQuestion Test********** \n\n");

		Question question = new Question(
                "Header1",
                "Content1",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER1",
                "USER1"
        );

		Question questionRes = questionRepository.save(question);

		questionRes.setContent("Content23");
		questionRes.setHeader("Header34");
		
		Assertions.assertEquals("Content23", questionRepository.save(questionRes).getContent(),
				"Successfully updated a Question");

		LOGGER.info("\n\n **********End updateQuestion Test********** \n\n");
	}

	@Test
	public void shouldGetAllQuestionsTest() {

		LOGGER.info("\n\n **********Start getAllQuestions Test********** \n\n");

		Question questionOne = new Question(
                "Header1",
                "Content1",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER1",
                "USER1"
        );
		
		Question questionTwo = new Question(
                "Header2",
                "Content2",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER2",
                "USER2"
        );

		questionRepository.save(questionOne);
		questionRepository.save(questionTwo);

		List<Question> questions = questionRepository.findAll();
		
		Assertions.assertEquals(2, questions.size(),
				"Successfully fetched the list of questions");

		Assertions.assertEquals("Header2" , questions.get(1).getHeader());

		
		LOGGER.info("\n\n **********End getAllQuestions Test********** \n\n");
	}

	@Test
	public void shouldDeleteQuestionTest() {

		LOGGER.info("\n\n **********Start deleteQuestion Test********** \n\n");

		Question questionOne = new Question(
                "Header1",
                "Content1",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER1",
                "USER1"
        );
		
		Question questionTwo = new Question(
                "Header2",
                "Content2",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER2",
                "USER2"
        );
		
		Question question = questionRepository.save(questionOne);
		questionRepository.save(questionTwo);

		questionRepository.deleteById(question.getId());
		
		Assertions.assertEquals(Optional.empty(), questionRepository.findById(question.getId()),
				"Successfully deleted a single question");

		LOGGER.info("\n\n **********End deleteQuestion Test********** \n\n");
	}

}
