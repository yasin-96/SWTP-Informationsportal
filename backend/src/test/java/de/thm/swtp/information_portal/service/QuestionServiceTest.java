package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.TagRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ComponentScan()
@DataMongoTest
@ContextConfiguration
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private Question question;


    @BeforeEach
    public void Setup() {
        questionRepository.deleteAll();
        tagRepository.deleteAll();

        var tags = List.of(new Tag("Tag1"), new Tag("Tag2"));

        tagRepository.save(tags.get(0));
        tagRepository.save(tags.get(1));

        question = new Question(
                "Header1",
                "Content1",
                List.of(new Tag("Tag1"), new Tag("Tag2")),
                "USER1",
                "USER1"
        );
        questionRepository.save(question);

        final List<Answer> answersList = List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2")
        );

        answerRepository.save(new Answers(answersList, question.getId()));
    }

    @Test
    @Order(1)
    public void shouldFindByTagNameTest() {

        ResponseEntity<HashSet<Question>> questionRes = questionService.findByTagName("Tag1");

        Assertions.assertNotNull(questionRes, "Successfully add a Question");
        Assertions.assertTrue(questionRes.hasBody());
        Assertions.assertEquals(200, questionRes.getStatusCodeValue());

    }


    @Test
    @Order(2)
    public void shouldFindTagTest() {

        List<Question> questionRes = questionService.findTag("TAG1");
        Assertions.assertNotNull(questionRes, "Successfully find tag");
        Assertions.assertTrue(!questionRes.isEmpty());
    }


    @Test
    @Order(3)
    public void shouldFindAllTagsTest() {

        ResponseEntity<List<Question>> questionRes = questionService.findAllTags("TAG1");
        Assertions.assertNotNull(questionRes);
        Assertions.assertTrue(questionRes.hasBody());
        Assertions.assertEquals(200, questionRes.getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void shouldFindAllQuestionsTest() {

        ResponseEntity<List<Question>> questionRes = questionService.getAllQuestions();

        Assertions.assertNotNull(questionRes);
        Assertions.assertEquals("Content1", questionRes.getBody().get(0).getContent());
        Assertions.assertEquals("Header1", questionRes.getBody().get(0).getHeader());
    }

    @Test
    @Order(5)
    public void shouldEditQuestionTest() {

        ResponseEntity<Question> questionRes = questionService.editQuestion(question);

        Assertions.assertNotNull(questionRes);
        Assertions.assertEquals("Content1", questionRes.getBody().getContent());
        Assertions.assertEquals("Header1", questionRes.getBody().getHeader());
    }

    @Test
    @Order(6)
    public void shouldPostQuestionTest() {

        ResponseEntity<Question> questionRes = questionService.postQuestion(question, "USER1", "USER1");

        Assertions.assertNotNull(questionRes);
        Assertions.assertEquals("USER1", questionRes.getBody().getUserId());
        Assertions.assertEquals("Header1", questionRes.getBody().getHeader());
    }

    @Test
    @Order(7)
    public void shouldFindQuestionTest() {

        ResponseEntity<Optional<Question>> questionRes = questionService.getQuestion(question.getId());

        Assertions.assertNotNull(questionRes);
        Assertions.assertEquals("USER1", questionRes.getBody().get().getUserId());
        Assertions.assertEquals("Header1", questionRes.getBody().get().getHeader());
    }

    @Test
    @Order(8)
    public void shouldMostActiveQuestionsTest() {

        ResponseEntity<List<Question>> questionRes = questionService.mostActiveQuestions();

        Assertions.assertNotNull(questionRes);
        Assertions.assertEquals("USER1", questionRes.getBody().get(0).getUserId());
        Assertions.assertEquals("Header1", questionRes.getBody().get(0).getHeader());
    }
}
