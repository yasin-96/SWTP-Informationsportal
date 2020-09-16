package de.thm.swtp.information_portal.service;
import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.models.Answer.UpdateAnswer;
import de.thm.swtp.information_portal.models.Comment.UpdateComment;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.ArrayList;
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

    private Answers answers;
    private List<Answer> answersList;

    @BeforeEach
    public void Setup() {

        answerRepository.deleteAll();

        answersList = List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2")
        );

        answers = new Answers(answersList, "Question1");
        answerRepository.save(answers);
    }

    @Test
    public void shouldAddAnswerWithValidData() {

        ResponseEntity<Answers> answersRes = answerService.add(answers, "user1", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertTrue(answersRes.hasBody());
        Assertions.assertEquals(200, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotAddAnswerWithMissingUserId() {

        ResponseEntity<Answers> answersRes = answerService.add(answers, "", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotAddAnswerWithMissingUserName() {

        ResponseEntity<Answers> answersRes = answerService.add(answers, "user1", "");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotAddAnswerWithAnswersList() {
        var copy = answers;
        copy.setListOfAnswers(new ArrayList());
        ResponseEntity<Answers> answersRes = answerService.add(answers, "user1", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }


    @Test
    public void shouldUpdateAnswerTest() {

        UpdateAnswer updateAnswer = new UpdateAnswer("Question1", answersList.get(0).getId(), "Content100");
        ResponseEntity<Answers> answersRes = answerService.updateAnswer(updateAnswer, "user1", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertTrue(answersRes.hasBody());
        Assertions.assertEquals(200, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateAnswerWithoutQuestionIDTest() {

        UpdateAnswer updateAnswer = new UpdateAnswer("", answersList.get(0).getId(), "Content100");
        ResponseEntity<Answers> answersRes = answerService.updateAnswer(updateAnswer, "user1", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateAnswerWithoutUserIDTest() {

        UpdateAnswer updateAnswer = new UpdateAnswer("Question1", answersList.get(0).getId(), "Content100");
        ResponseEntity<Answers> answersRes = answerService.updateAnswer(updateAnswer, "", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateAnswerWithoutUserNameTest() {

        UpdateAnswer updateAnswer = new UpdateAnswer("Question1", answersList.get(0).getId(), "Content100");
        ResponseEntity<Answers> answersRes = answerService.updateAnswer(updateAnswer, "user1", "");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateAnswerWithoutNewAnswerTest() {

        ResponseEntity<Answers> answersRes = answerService.updateAnswer(new UpdateAnswer("", "", 0 ), "user1", "user1");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateCommentsWithOutAnswerData() {

        var answerRes = answerService.updateAnswer(new UpdateAnswer("Answer1", "", 0), "USER1", "");
        var answerRes2 = answerService.updateAnswer(new UpdateAnswer("", answersList.get(0).getId(), 0), "USER1", "");
        var answerRes3 = answerService.updateAnswer(new UpdateAnswer(null, null, 0), "USER1", "");
        var answerRes4 = answerService.updateAnswer(new UpdateAnswer("Answer1", null, 0), "USER1", "");
        var answerRes5 = answerService.updateAnswer(new UpdateAnswer(null, answersList.get(0).getId(), 0), "USER1", "");
        var answerRes6 = answerService.updateAnswer(new UpdateAnswer(null, null, null), "USER1", "");

        Assertions.assertNotNull(answerRes);
        Assertions.assertNull(answerRes.getBody());
        Assertions.assertEquals(400, answerRes.getStatusCodeValue());

        Assertions.assertNotNull(answerRes2);
        Assertions.assertNull(answerRes2.getBody());
        Assertions.assertEquals(400, answerRes2.getStatusCodeValue());

        Assertions.assertNotNull(answerRes3);
        Assertions.assertNull(answerRes3.getBody());
        Assertions.assertEquals(400, answerRes3.getStatusCodeValue());

        Assertions.assertNotNull(answerRes4);
        Assertions.assertNull(answerRes4.getBody());
        Assertions.assertEquals(400, answerRes4.getStatusCodeValue());

        Assertions.assertNotNull(answerRes5);
        Assertions.assertNull(answerRes5.getBody());
        Assertions.assertEquals(400, answerRes5.getStatusCodeValue());

        Assertions.assertNotNull(answerRes6);
        Assertions.assertNull(answerRes6.getBody());
        Assertions.assertEquals(400, answerRes6.getStatusCodeValue());


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
    public void shouldFindAnswerToEditWithoutQuestionIdTest() {
        ResponseEntity<Optional<Answer>> answersRes = answerService.getAnswerToEdit("", answersList.get(0).getId());

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldFindAnswerToEditWithoutAnswerIdTest() {
        ResponseEntity<Optional<Answer>> answersRes = answerService.getAnswerToEdit("Question1", "");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotFindAnswerToEditWithoutTest() {
        ResponseEntity<Optional<Answer>> answersRes = answerService.getAnswerToEdit("Question100000", "Answer100000");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(404, answersRes.getStatusCodeValue());
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

    @Test
    public void shouldNotGetAnswersByIdTest() {
        ResponseEntity<Optional<Answers>> answersRes = answerService.getAnswers("");

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(400, answersRes.getStatusCodeValue());
    }

    @Test
    @AfterTestClass
    public void shouldNotGetAnswersByIdListIsEmptyTest() {

        answerRepository.deleteAll();
        ResponseEntity<Optional<Answers>> answersRes = answerService.getAnswers(answers.getId());

        Assertions.assertNotNull(answersRes);
        Assertions.assertNull(answersRes.getBody());
        Assertions.assertEquals(404, answersRes.getStatusCodeValue());
    }
}

