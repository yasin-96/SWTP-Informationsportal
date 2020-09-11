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

import java.util.*;
import java.util.stream.Collectors;

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
    public void shouldNotFindByTagNameTest() {
        ResponseEntity<HashSet<Question>> questionRes = questionService.findByManyTagNames("NichtVorhanden");

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(404, questionRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotFindByTagNameThenParameterIsNullTest() {

        ResponseEntity<HashSet<Question>> questionRes = questionService.findByManyTagNames("");

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());

    }


    @Test
    @Order(1)
    public void shouldFindByTagNameTest() {

        ResponseEntity<HashSet<Question>> questionRes = questionService.findByManyTagNames("Tag1");

        Assertions.assertNotNull(questionRes, "Successfully add a Question");
        Assertions.assertTrue(questionRes.hasBody());
        Assertions.assertEquals(200, questionRes.getStatusCodeValue());

    }

    @Test
    public void shouldFindByTagNameWithManySearchqueryTest() {
        tagRepository.saveAll(List.of(new Tag("Tag300"), new Tag("asdfghjkl")));
        ResponseEntity<HashSet<Question>> questionRes = questionService.findByManyTagNames("Tag300 Tag200");


        Assertions.assertNotNull(questionRes);
        Assertions.assertTrue(questionRes.hasBody());
        var resp = questionRes.getBody();
        Assertions.assertTrue(resp.size() == 2);
        //var t1 = resp.gegetTags().get(0).getName());
        //var t2 = questionRes.getBody().stream().map(t -> t.getTags().get(1).getName()).toString();
        //Assertions.assertEquals("TAG1", t1.toString());
        //Assertions.assertEquals("TAG2", t2);
        Assertions.assertEquals(200, questionRes.getStatusCodeValue());

    }

    @Test
    public void shouldNotFindByTagNameWithManySearchqueryTest() {

        ResponseEntity<HashSet<Question>> questionRes = questionService.findByManyTagNames("T2341 asd");

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(404, questionRes.getStatusCodeValue());

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

        ResponseEntity<HashSet<Question>> questionRes = questionService.findAllTags("TAG1");
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
        Assertions.assertEquals(200, questionRes.getStatusCodeValue());

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
    public void shouldNotEditQuestionWithoutUserIDTest() {
        var copy = question;
        copy.setUserId("");
        ResponseEntity<Question> questionRes = questionService.editQuestion(question);

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotEditQuestionWithoutUserNameTest() {
        var copy = question;
        copy.setUserName("");
        ResponseEntity<Question> questionRes = questionService.editQuestion(question);

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotEditQuestionWithoutOnePropIsEmptyTest() {
        var copy = question;
        copy.setHeader("");
        ResponseEntity<Question> questionRes = questionService.editQuestion(question);

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());
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
    public void shouldNotPostQuestionWithOutUserIdTest() {

        ResponseEntity<Question> questionRes = questionService.postQuestion(question, "", "USER1");

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotPostQuestionWithOutUserNameTest() {

        ResponseEntity<Question> questionRes = questionService.postQuestion(question, "USER1", "");

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotPostQuestionWithOneEmptyPropTest() {
        var copy = question;
        copy.setHeader("");
        ResponseEntity<Question> questionRes = questionService.postQuestion(question, "USER1", "");

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());
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
    public void shouldNotFindQuestionTest() {

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

    @Test
    @Order(9)
    public void shouldNotFindQuestionIfIdIsEmptyTest() {

        ResponseEntity<Optional<Question>> questionRes = questionService.getQuestion("");

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(400, questionRes.getStatusCodeValue());
    }

    @Test
    @Order(10)
    public void shouldNotFindAllQuestionsIsEmptyTest() {
        questionRepository.deleteAll();
        ResponseEntity<List<Question>> questionRes = questionService.getAllQuestions();

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(404, questionRes.getStatusCodeValue());
    }

    @Test
    @Order(11)
    public void shouldNotFindQuestionIfQuestionWasNotFoundTest() {
        questionRepository.deleteAll();
        ResponseEntity<Optional<Question>> questionRes = questionService.getQuestion(question.getId());

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(404, questionRes.getStatusCodeValue());
    }

    @Test
    @Order(12)
    public void shouldNotFoundMostActiveQuestionsTest() {
        questionRepository.deleteAll();
        ResponseEntity<List<Question>> questionRes = questionService.mostActiveQuestions();

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(404, questionRes.getStatusCodeValue());
    }

    @Test
    @Order(13)
    public void shouldNotFoundMostActiveQuestionsThenAnswersIsNullTest() {
        questionRepository.saveAll(List.of(question, question));
        answerRepository.deleteAll();
        ResponseEntity<List<Question>> questionRes = questionService.mostActiveQuestions();

        Assertions.assertNotNull(questionRes);
        Assertions.assertNull(questionRes.getBody());
        Assertions.assertEquals(404, questionRes.getStatusCodeValue());
    }

    @Test
    @Order(14)
    public void shouldCheckInternalMostActiveQuestionsMethodTest() {
        questionRepository.saveAll(List.of(question, question));

        final List<Answer> answersList = List.of(
                new Answer("Answer1", 10, "user1", "user1"),
                new Answer("Answer2", 12, "user2", "user2"),
                new Answer("Answer3", 12, "user3", "user2"),
                new Answer("Answer3ASDLKAJ", 12, "user4", "user2"),
                new Answer("Answer3ASDLKAJOHJSDFH", 12, "user4", "user2"),
                new Answer("Answer3ASDLKAJOHJSDFHHSFJDHFJS", 12, "user4", "user2"),
                new Answer("Answer3ASDLKAJOHJSDFHHSFJDHFJSJASDHKAHD", 12, "user4", "user2"),
                new Answer("Answer4ASDLKAJ", 12, "user5", "user2"),
                new Answer("Answer4ASDLKAJOHJSDFH", 12, "user5", "user2"),
                new Answer("Answer4ASDLKAJOHJSDFHHSFJDHFJS", 12, "user5", "user2"),
                new Answer("Answer4ASDLKAJOHJSDFHHSFJDHFJSJASDHKAHD", 12, "user5", "user2"),
                new Answer("Answer5ASDLKAJ", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFH", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJS", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJSJASDHKAHD", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJ", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFH", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJS", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJSJASDHKAHD", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJ", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFH", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJS", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJSJASDHKAHD", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJ", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFH", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJS", 12, "user6", "user2"),
                new Answer("Answer5ASDLKAJOHJSDFHHSFJDHFJSJASDHKAHD", 12, "user6", "user2"),
                new Answer("Answer5", 12, "user6", "user2"),
                new Answer("Answer5", 12, "user6", "user2"),
                new Answer("Answer544", 12, "user6", "user2"),
                new Answer("Answer4563", 12, "user3", "user2"),
                new Answer("Answer4564563", 12, "user4", "user2"),
                new Answer("Answer4565464", 12, "user5", "user2"),
                new Answer("Answer456465", 12, "user6", "user2"),
                new Answer("Answer4565465", 12, "user6", "user2"),
                new Answer("Answer456465", 12, "user6", "user2"),
                new Answer("Answer4565", 12, "user6", "user2"),
                new Answer("Answer4565", 12, "user6", "user2"),
                new Answer("Answer4565", 12, "user6", "user2"),
                new Answer("Answer564565", 12, "user6", "user2"),
                new Answer("Answe5", 12, "user6", "user2"),
                new Answer("Answer5", 12, "user6", "user2"),
                new Answer("Answer445", 12, "user6", "user2"),
                new Answer("Answer225", 12, "user6", "user2"),
                new Answer("Answer223", 12, "user3", "user2"),
                new Answer("Answer223", 12, "user4", "user2"),
                new Answer("Answerasd54", 12, "user5", "user2"),
                new Answer("Answesaddr5", 12, "user6", "user2"),
                new Answer("Answsaaser5", 12, "user6", "user2"),
                new Answer("Answsader5", 12, "user6", "user2"),
                new Answer("Ansasdwer5", 12, "user6", "user2"),
                new Answer("Answer5", 12, "user6", "user2"),
                new Answer("Answehfr5", 12, "user6", "user2"),
                new Answer("Ansasdwer5", 12, "user6", "user2"),
                new Answer("Ansafghsdwer5", 12, "user6", "user2"),
                new Answer("Ansasdwer2", 12, "user2", "user2"),
                new Answer("Ansswer3", 12, "user3", "user2"),
                new Answer("Answer3", 12, "user4", "user2"),
                new Answer("Answer4", 12, "user5", "user2"),
                new Answer("Answfgher4", 12, "user5", "user2"),
                new Answer("Answer4", 12, "user5", "user2"),
                new Answer("Ansfghwfher4", 12, "user5", "user2"),
                new Answer("Ansfghwer4", 12, "user5", "user2"),
                new Answer("Answhfger4", 12, "user5", "user2"),
                new Answer("Answhfghfeasdr4", 12, "user5", "user2"),
                new Answer("An827394ssasdwer5", 12, "user6", "user2"),
                new Answer("An88889023456789xdtcfzgvbuh27394ssasdwer5", 12, "user6", "user2"),
                new Answer("An828889023456789xdtcfzgvbuh7394ssasdwer5", 12, "user6", "user2"),
                new Answer("An8278889023456789xdtcfzgvbuh394ssasdwer5", 12, "user6", "user2"),
                new Answer("An82738889023456789xdtcfzgvbuh94ssasdwer5", 12, "user6", "user2"),
                new Answer("An827398889023456789xdtcfzgvbuh4ssasdwer5", 12, "user6", "user2"),
                new Answer("A8889023456789xdtcfzgvbuhn827394ssasdwer5", 12, "user6", "user2"),
                new Answer("An8889023456789xdtcfzgvbuh827394ssasdwer5", 12, "user6", "user2"),
                new Answer("An827394s8889023456789xdtcfzgvbuhswer5", 12, "user6", "user2")
        );

        answerRepository.save(new Answers(answersList, question.getId()));

        ResponseEntity<List<Question>> questionRes = questionService.mostActiveQuestions();

        Assertions.assertNotNull(questionRes);
        Assertions.assertTrue(questionRes.getBody().size() <= 20);
        Assertions.assertEquals(200, questionRes.getStatusCodeValue());

        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }

}
