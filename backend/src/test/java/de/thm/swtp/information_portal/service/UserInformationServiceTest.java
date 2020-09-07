package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.models.User.UserInformation;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.CommentRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserInformationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.net.URISyntaxException;
import java.util.List;

@ComponentScan()
@DataMongoTest
@ContextConfiguration
class UserInformationServiceTest {

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void Setup() {

        //clear collection in database
        userInformationRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();
        commentRepository.deleteAll();

        var qOneId = questionRepository.save(
                new Question(
                        "Header1",
                        "Content1",
                        List.of(new Tag("Tag1"), new Tag("Tag2")),
                        "User1",
                        "USER1")
        );
        var qTwoId = questionRepository.save(
                new Question(
                        "Header2",
                        "Content2",
                        List.of(new Tag("Tag3"), new Tag("Tag3")),
                        "User1",
                        "USER1")
        );
        var qThreeId = questionRepository.save(
                new Question(
                        "Header3",
                        "Content3",
                        List.of(new Tag("Tag4"), new Tag("Tag5")),
                        "User1",
                        "USER1")
        );

        final List<Answer> answersList = List.of(
                new Answer("Answer1", 10, "User1", "USER1"),
                new Answer("Answer2", 12, "User2", "USER2")
        );
        answerRepository.saveAll(List.of(
                new Answers(answersList, qOneId.getId()),
                new Answers(answersList, qThreeId.getId())
        ));
    }

    @Test
    public void shouldFindUserInfoTest() throws URISyntaxException {

        var userInformation = userInformationService.getUserInfo("User1");
        var userInformationTwo = userInformationService.getUserInfo("User2");
        var userInformationUnknown = userInformationService.getUserInfo("User4");

        Assertions.assertNotNull(userInformation);
        Assertions.assertEquals("User1", userInformation.getBody().getId());
        Assertions.assertTrue(userInformation.hasBody());
        Assertions.assertEquals(2, userInformation.getBody().getNumberOfAnswers());
        Assertions.assertEquals(3, userInformation.getBody().getNumberOfQuestions());
        Assertions.assertEquals(200, userInformation.getStatusCodeValue());

        Assertions.assertNotNull(userInformationTwo);
        Assertions.assertEquals("User2", userInformationTwo.getBody().getId());
        Assertions.assertTrue(userInformationTwo.hasBody());
        Assertions.assertEquals(2, userInformationTwo.getBody().getNumberOfAnswers());
        Assertions.assertEquals(0, userInformationTwo.getBody().getNumberOfQuestions());
        Assertions.assertEquals(200, userInformationTwo.getStatusCodeValue());

        //TODO hier müssen wir noch mal drüber schauen
        Assertions.assertNull(userInformationUnknown);
        Assertions.assertEquals("User4", userInformationUnknown.getBody().getId());
        Assertions.assertTrue(userInformationUnknown.hasBody());
        Assertions.assertEquals(0, userInformationUnknown.getBody().getNumberOfAnswers());
        Assertions.assertEquals(0, userInformationUnknown.getBody().getNumberOfQuestions());
        Assertions.assertEquals(200, userInformationUnknown.getStatusCodeValue());

    }
}
