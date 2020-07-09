package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.*;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class SocketController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/hello")
    @SendTo("/notify")

    public CompletableFuture<ResponseEntity<String>> socketResponse(@RequestBody String wsMessage) {

        System.out.println(wsMessage);

        try {
            var parseJsObject = new ObjectMapper();

            SocketReceived wsData = parseJsObject.readValue(wsMessage, SocketReceived.class);
            Optional<Answers> answers = answerRepository.findById(wsData.getQuestionId());
            var users = new HashSet<User>();
            List<Answer> answersOfQuestion = answers.get().getListOfAnswers();
            for (Answer answer : answersOfQuestion) {
                Optional<User> userToBeAdded = userRepository.findById(answer.getUserId());
                users.add(userToBeAdded.get());
            }
            String headerOfQuestion = questionRepository.findById(wsData.getQuestionId()).get().getHeader();
            SocketResponse socketResponse = new SocketResponse(wsData.getQuestionId(), users, headerOfQuestion,
                    wsData.getIsAnswer(), wsData.getIsComment(), wsData.getMinimalUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return CompletableFuture.completedFuture(new ResponseEntity<String>(wsMessage, HttpStatus.OK));
    }
}
