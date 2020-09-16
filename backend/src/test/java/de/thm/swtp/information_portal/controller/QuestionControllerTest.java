//package de.thm.swtp.information_portal.controller;//package de.thm.swtp.information_portal.Controller;

//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.Instant;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import de.thm.swtp.information_portal.controller.QuestionController;
//import de.thm.swtp.information_portal.models.Answer.Answer;
//import de.thm.swtp.information_portal.models.Answer.Answers;
//import de.thm.swtp.information_portal.models.Question.Question;
//import de.thm.swtp.information_portal.models.Tag.Tag;
//import de.thm.swtp.information_portal.service.AnswerService;
//import de.thm.swtp.information_portal.service.QuestionService;
//
//@WebMvcTest(QuestionController.class)
//@TestMethodOrder(OrderAnnotation.class)
//class QuestionControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private QuestionService questionService;
//
//    @MockBean
//    private AnswerService answerService;
//
//    private final String URL = "/api/";
//    private String key = "73mvQo97Iwhkd13C2iQO88UyPG7LNe56";
//
//    private String jwtTokenAsString = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1OTk1MDIxNjYsImV4cCI6MTYzMTAzODE2NiwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.xv4iAMVJT1y_snOzNTRn5b_SbMP-mNkhrx26hdQHVjo";
//    private String jwtTokenAsStringTWO = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJER3UtWWk3amdMN3hYV2ZDRXE4MHl6OFZRRFZ4UDI3dTdkeFlMUC1fb3lNIn0.eyJleHAiOjE1OTk1MDMwMDcsImlhdCI6MTU5OTUwMjcwNywiYXV0aF90aW1lIjoxNTk5NDk4NDkzLCJqdGkiOiI0NzI0YTEzZi05NDcwLTQzYWItOTdkNS0wM2NjYzQ0NzI2ZmMiLCJpc3MiOiJodHRwczovL3N3dHAucGN2b2xrbWVyLmRlL2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIxMWJlZWQ3Ny01YWI2LTQ5NWUtYmFkZi1lOTBlYzBjODkyN2MiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJnYXRld2F5Iiwibm9uY2UiOiJHM1FIal9wb0IzNE1NT3VBMXhhQmZHZFBTRWYzR0hNcllhbURpd2JVMDdZIiwic2Vzc2lvbl9zdGF0ZSI6IjE5MGJkNGQ2LWQxYmUtNDZiMC05YmUyLTQ3Y2ZkZDEyMTQ0NSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiaHR0cHM6Ly9zd3RwLnBjdm9sa21lci5kZSIsImh0dHA6Ly9zd3RwLml0Y2hpZWZzLmRlIiwiaHR0cDovL3N3dHAuaXRjaGllZnMuZGU6MzIwMCIsImh0dHA6Ly8xOTIuMTY4LjE3OC4yNTo4MDgwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkFsZXhhbmRlciBKYWp6bGVyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWpqejkwIiwiZ2l2ZW5fbmFtZSI6IkFsZXhhbmRlciIsImZhbWlseV9uYW1lIjoiSmFqemxlciIsImVtYWlsIjoiYWxleGFuZGVyLmphanpsZXJAZ21haWwuY29tIn0.M9UwJxpzfGMsxR9tQxJi5z5r9gdFil1Q0azIXBfUdgve1TUWmXd8uF0zJXJ8N-vx0ipY66nTuW0bKA34Yz1eT_O3Z4OQufVYr8ncX1R47ZTXzYffvT9qRi59qqoQZEUiWwCjVQDEx9ub8xN58_0RWVZEnMvRs3DXYC-zvDVhns1Thza9XCfbkH22Wvp2Fd-fLwfQP97fL6J7ySSQskSjEDuKtwaqnKR1JpTAWeK-x9-6nOBaM6-XDVUgCO0hbeMX8u1n6xF1wV4_6u6kqEaqRi7R29X5nsfmvXE99jVIgfpvcf-ih0SIpg7PoeB6ht_MJn7iA0Os56Mkcb8U2hkiQQ";
//
//    /**
//     * Test method for finding all Questions
//     *
//     * @throws Exception
//     */
//    @Test
//    @Order(1)
//    public void shouldFindAllQuestionsTest() throws Exception {
//
//        var questions = List.of(
//                new Question(
//                        "Header1",
//                        "Content1",
//                        List.of(
//                                new Tag("Tag1"),
//                                new Tag("Tag2")
//                        ),
//                        "userOneId",
//                        "userOne"
//                ),
//                new Question(
//                        "Header1",
//                        "Content1",
//                        List.of(
//                                new Tag("Tag33"),
//                                new Tag("Tag22")
//                        ),
//                        "userTwoId",
//                        "userTwo"
//                )
//        );
//
//
//        when(questionService.getAllQuestions())
//             .thenReturn((ResponseEntity<List<Question>>) questions);
//
//
//        MvcResult mvcResult = mockMvc.perform(get(URL + "allQuestions")
//                .header("Authorization", "Bearer " + jwtTokenAsString)
//                //.characterEncoding(key)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].header").value("Header1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content").value("Content1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("[0].tags").isArray());
//    }

//    /**
//     * Test method for finding Question by ID
//     *
//     * @throws Exception
//     */
//    @Test
//    @Order(2)
//    public void shouldFindQuestionTest() throws Exception {
//        Question createdQuestion = createQuestionList("Header2", "Content2", "Tag1", "Tag2");
//        when(questionService.getQuestion(createdQuestion.getId())).thenReturn(Optional.of(createdQuestion));
//
//        MvcResult mvcResult = mockMvc.perform(
//                get(URL + "questionById/{id}", createdQuestion.getId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").value(createdQuestion.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value("Header2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Content2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray());
//    }
//
//    /**
//     * Test method for finding Question by tag
//     *
//     * @throws Exception
//     */
//    @Test
//    @Order(2)
//    public void shouldfindByTagTest() throws Exception {
//
//        when(questionService.findByTag("Tag1")).thenReturn(
//                Arrays.asList(createQuestionList("Header1", "Content1", "Tag1", "Tag2"),
//                        createQuestionList("Header2", "Content2", "Tag1", "Tag2"))
//        );
//
//        MvcResult mvcResult = mockMvc.perform(
//                get(URL + "questionByTag/{tag}", "Tag1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].header").value("Header1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content").value("Content1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tags").isArray());
//    }
//
//    @Test
//    public void shouldEditQuestionTest() throws Exception {
//
//        Optional<Question> question = Optional.of(createQuestionList("Header1", "Content1", "Tag1", "Tag2"));
//        when(questionService.editQuestion(question.get())).thenReturn(question.get());
//        question.get().setHeader("Header 20");
//        List<Tag> tags = question.get().getTags();
//        tags.get(0).setName("tag 22");
//        //	tags.get(0).setCounter(122);
//        question.get().setTags(tags);
//
//        MvcResult mvcResult = mockMvc.perform(
//                put(URL + "question")
//                        .content(asJsonString(question.get()))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value("Header 20"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Content1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray());
//    }
//
//    @Test
//    public void shouldGetDataByQueryTest() throws Exception {
//
//        when(questionService.findByTag("Tag1")).thenReturn(
//                Arrays.asList(createQuestionList("Header1", "Content1", "Tag1", "Tag2"),
//                        createQuestionList("Header2", "Content2", "Tag1", "Tag2"))
//        );
//
//        MvcResult mvcResult = mockMvc.perform(
//                get(URL + "question/query")
//                        .param("searchQuery", "Tag1 Tag2")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].header").value("Header1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content").value("Content1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tags").isArray());
//    }
//
//    /**
//     * Testing of PostQuestion method
//     *
//     * @throws Exception
//     */
//    @Test
//    @Order(3)
//    public void shouldPostQuestionTest() throws Exception {
//        Question question = createQuestionList("Header4", "Content4", "Tag3", "Tag4");
//        when(questionService.postQuestion(question)).thenReturn(question);
//
//        MvcResult mvcResult = mockMvc.perform(
//                post(URL + "newQuestion")
//                        .content(asJsonString(question))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value("Header4"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Content4"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray());
//
//    }
//
//
//    @Test
//    public void shouldGetMostActiveQuestionsTest() throws Exception {
//
//        List<Question> questions = Arrays.asList(createQuestionList("Header1", "Content1", "Tag1", "Tag2"),
//                createQuestionList("Header2", "Content2", "Tag1", "Tag2"));
//
//
//        Optional<Answers> answers = Optional.of((new Answers(Arrays.asList(new Answer("Ans1", 12), new Answer("Ans2", 22)),
//                questions.get(0).getId())));
//
//        when(questionService.getAllQuestions()).thenReturn(questions);
//        when(answerService.findByQuestionId(questions.get(0).getId())).thenReturn(answers);
//
//        MvcResult mvcResult = mockMvc.perform(
//                get(URL + "question/getMostActiveQuestions")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].header").value("Header1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content").value("Content1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("[0].tags").isArray());
//    }
//
//    /**
//     * Convert object to Json
//     *
//     * @param obj
//     * @return a value as String
//     */
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * @return Question with tags
//     */
//    private Question createQuestionList(String header, String content, String tagOne, String tagTwo) {
//        final Question question = new Question(header, content, Arrays.asList(new Tag(tagOne), new Tag(tagTwo)));
//        return question;
//    }
//}
