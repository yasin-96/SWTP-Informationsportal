//package de.thm.swtp.information_portal.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import de.thm.swtp.information_portal.controller.AnswerController;
//import de.thm.swtp.information_portal.models.Answer.Answer;
//import de.thm.swtp.information_portal.models.Answer.Answers;
//import de.thm.swtp.information_portal.service.AnswerService;
//import de.thm.swtp.information_portal.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static de.thm.swtp.information_portal.Util.Util.generateJWTToken;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AnswerController.class)
//@AutoConfigureMockMvc
//@EnableWebMvc
//class AnswerControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AnswerService answerService;
//
//    @MockBean
//    private UserService userService;
//
//    private final String URL = "/api/";
//
//    private String jwtToken;
//
//    private String uuidForQuestion;
//    private String uuidForAnswerOne;
//    private String uuidForAnswerTwo;
//    private String uuidForUser;
//    private String uuidForUserTwo;
//
//    @BeforeEach
//    public void Setup() {
//        uuidForQuestion =  "QQQQQQQQ-0000-0000-0000-111111111111";
//        uuidForAnswerOne = "AAAAAAAA-0000-0000-0000-000000000001";
//        uuidForAnswerTwo = "AAAAAAAA-0000-0000-0000-000000000002";
//        uuidForUser =     "UUUUUUUU-0000-USER-0000-000000000001";
//        uuidForUserTwo =     "UUUUUUUU-0000-USER-0000-000000000002";
//
//        jwtToken = generateJWTToken();
//    }
//
//
//
//    @Test
//    public void postAnswerWithVALIDRequestBodyCreateNewAnswerAndJWT() throws Exception {
//
//        Answers answers = new Answers(
//                List.of(
//                        new Answer("Answer3", uuidForUser, 10),
//                        new Answer("Answer4", uuidForUserTwo, 20)),
//                uuidForQuestion);
//
//        Optional<Answers> optional = Optional.of(new Answers(createAnswersList(), uuidForQuestion));
//        when(answerService.findByQuestionId(uuidForQuestion)).thenReturn(optional);
//
//        MvcResult mvcResult = mockMvc.perform(
//                post(URL + "answer")
//                        .header("Authorization", "Bearer " + jwtToken)
//                        .content(asJsonString(answers))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").value("Q2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer3"));
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    /**
//     * Test method for finding answers by ID
//     *
//     * @throws Exception
//     */
//    @Test
//    public void shouldFindAnswersTest() throws Exception {
//
//        System.out.println("Token: " + jwtToken + "\n");
//
//        Optional<Answers> optional = Optional.of(new Answers(createAnswersList(), "Q1"));
//        when(answerService.findByQuestionId("Q1")).thenReturn(optional);
//
//        MvcResult mvcResult = mockMvc.perform(
//                get(URL + "answersByQuestionId/{id}", "Q1")
//                .header("Authorization", "Bearer " + jwtToken)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").value("Q1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer1"));
//    }
//
//    @Test
//    public void shouldIncreaseAnswerRatingTest() throws Exception {
//
//        System.out.println("TOken: " + jwtToken + "\n");
//
//
//        Optional<Answers> optional = Optional.of(new Answers(createAnswersList(), "Q1"));
//        when(answerService.findByQuestionId("Q1")).thenReturn(optional);
//
//        MvcResult mvcResult = mockMvc.perform(
//                post(URL + "/answer/increaseRating")
//                        .content(asJsonString(optional.get()))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").value("Q1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer1"));
//    }
//
////    /**
////     * Testing of PostAnswers method
////     *
////     * @throws Exception
////     */
////    @Test
////    public void shouldPostAnswerTest() throws Exception {
////        Answers answers = new Answers(
////                Arrays.asList(new Answer("Answer3", 10),
////                        new Answer("Answer4", 20)), "Q2");
////
////        MvcResult mvcResult = mockMvc.perform(
////                post(URL + "answer")
////                        .content(asJsonString(answers))
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andExpect(request().asyncStarted())
////                .andDo(MockMvcResultHandlers.log())
////                .andReturn();
////
////        mockMvc.perform(asyncDispatch(mvcResult))
////                .andDo(print())
////                .andExpect(status().isCreated())
////                .andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
////                .andExpect(MockMvcResultMatchers.jsonPath("id").value("Q2"))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("Answer3"));
////
////    }
//
//    @Test
//    public void shouldGetAnswerToBeEditedTest() throws Exception {
//        Optional<Answers> answersOne = Optional.of(new Answers(createAnswersList(), "Q1"));
//        when(answerService.findByQuestionId("Q1")).thenReturn(answersOne);
//
//        String[] ids = {answersOne.get().getId(), answersOne.get().getListOfAnswers().get(1).getId()};
//
//        MvcResult mvcResult = mockMvc.perform(
//                get(URL + "answer/answerTobeEdited")
//                        .content(asJsonString(ids))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Answer2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value("20"));
//    }
//
//
//    @Test
//    public void shouldEditAnswerTest() throws Exception {
//
//        Optional<Answers> answers = Optional.of(new Answers(createAnswersList(), "Q1"));
//        when(answerService.findByQuestionId("Q1")).thenReturn(answers);
//
//        List<Answer> answersList = answers.get().getListOfAnswers();
//        answersList.get(0).setContent("new Content");
//        answersList.get(0).setRating(120);
//        answers.get().setListOfAnswers(answersList);
//
//        MvcResult mvcResult = mockMvc.perform(
//                put(URL + "answer/{id}", answers.get().getId())
//                        .content(asJsonString(answers.get()))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(request().asyncStarted())
//                .andDo(MockMvcResultHandlers.log())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("listOfAnswers").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").value("Q1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfAnswers.[0].content").value("new Content"));
//    }
//
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
//
//    /**
//     * @return list of Answers
//     */
//    private List<Answer> createAnswersList() {
//        return Arrays.asList(
//                new Answer("Answer1", uuidForUser, 10),
//                new Answer("Answer2", uuidForUserTwo, 20)
//        );
//    }
//}
