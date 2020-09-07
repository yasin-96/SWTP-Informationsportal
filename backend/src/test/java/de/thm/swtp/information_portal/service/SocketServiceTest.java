package de.thm.swtp.information_portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.models.Comment.Comment;
import de.thm.swtp.information_portal.models.Comment.Comments;
import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.Socket.SocketReceived;
import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.models.User.MinimalUser;
import de.thm.swtp.information_portal.models.User.User;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.CommentRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.net.URISyntaxException;
import java.util.List;

@ComponentScan()
@DataMongoTest
@ContextConfiguration
class SocketServiceTest {

    @Autowired
    private SocketService socketService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    Question question;

    Answers answers;

    @BeforeEach
    public void Setup() {

        answerRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
        commentRepository.deleteAll();

        var tags = List.of(new Tag("Tag1"), new Tag("Tag2"));

        question = new Question("Header1", "Content1", tags, "USER1", "USER1");
        questionRepository.save(question);

        final List<Answer> answersList = List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2")
        );


        answers = new Answers(answersList, question.getId());
        answerRepository.save(answers);

        User userTwo = new User("User2", "USER2", "user2@gmail.com", "usr2");
        User userThree = new User("User3", "USER3", "user3@gmail.com", "usr3");
        userRepository.save(userTwo);
        userRepository.save(userThree);

        var commentsList = List.of(
                new Comment("Comment1", "USER1", "USER1", 10),
                new Comment("Comment2", "USER2", "USER2", 20)
        );

        var comments = new Comments(commentsList, "Answer1");
        commentRepository.save(comments);

    }

    @Test
    public void shouldCreateMessageTest() throws URISyntaxException {

        var socketReceived = new SocketReceived(
                question.getId(),
                answers.getId(),
                true,
                true,
                new MinimalUser(
                        "User1",
                        "user1"
                )
        );

        var socketResponse = socketService.createMessage(asJsonString(socketReceived));

        Assertions.assertNotNull(socketResponse);
        Assertions.assertTrue(socketResponse.hasBody());
        Assertions.assertEquals(200, socketResponse.getStatusCodeValue());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
