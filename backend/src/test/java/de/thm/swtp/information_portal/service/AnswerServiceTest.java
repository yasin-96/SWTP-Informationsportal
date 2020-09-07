package de.thm.swtp.information_portal.service;
import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.models.Answer.UpdateAnswer;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@ComponentScan()
@DataMongoTest
@ContextConfiguration
class AnswerServiceTest {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;


    @BeforeEach
    public void Setup() {
        answerRepository.deleteAll();
    }

    @Test
    public void shouldAddAnswerWithValidData() {
        final List<Answer> answersList = List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2")
        );

        Answers answers = new Answers(answersList, "Question1");
        answerRepository.save(answers);

        ResponseEntity<Answers> answersRes = answerService.add(answers, "user1", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertTrue(answersRes.hasBody());
        Assertions.assertEquals(200, answersRes.getStatusCodeValue());

    }

    @Test
    public void shouldUpdateAnswerTest() {
        final List<Answer> answersList = List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2")
        );

        Answers answers = new Answers(answersList, "Question1");
        answerRepository.save(answers);

        UpdateAnswer updateAnswer = new UpdateAnswer("Question1", answersList.get(0).getId(), "Content100");
        ResponseEntity<Answers> answersRes = answerService.updateAnswer(updateAnswer, "user1", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertTrue(answersRes.hasBody());
        Assertions.assertEquals(200, answersRes.getStatusCodeValue());
    }


    @Test
    public void shouldFindAnswerToEditTest() {
        final List<Answer> answersList = List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2")
        );

        Answers answers = new Answers(answersList, "Question1");
        answerRepository.save(answers);

        ResponseEntity<Optional<Answer>> answersRes = answerService.getAnswerToEdit("Question1", answersList.get(0).getId());

        Assertions.assertNotNull(answersRes);
        Assertions.assertTrue(answersRes.hasBody());
        Assertions.assertEquals(200, answersRes.getStatusCodeValue());
        Assertions.assertEquals("Answer1", answersRes.getBody().get().getContent());
        Assertions.assertEquals(10, answersRes.getBody().get().getRating());
    }

    @Test
    public void shouldGetAnswersByIdTest() {
        Answers answers = new Answers(List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2")
        ), "Question1");

        answerRepository.save(answers);

        ResponseEntity<Optional<Answers>> answersRes = answerService.getAnswers(answers.getId());

        Assertions.assertNotNull(answersRes);
        Assertions.assertTrue(answersRes.hasBody());
        Assertions.assertEquals("Answer2", answersRes.getBody().get().getListOfAnswers().get(0).getContent());
        Assertions.assertEquals(12, answersRes.getBody().get().getListOfAnswers().get(0).getRating());
    }
}

