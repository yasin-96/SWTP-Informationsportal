package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.*;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<ResponseEntity<SocketResponse>> socketResponse(@RequestBody String id){
        Optional<Answers> answers = answerRepository.findById(id);
        var users = new HashSet<User>();
        List<Answer> answersOfQuestion = answers.get().getListOfAnswers();
        for(Answer answer:answersOfQuestion){
                Optional<User> userToBeAdded = userRepository.findById(answer.getUserId());
                users.add(userToBeAdded.get());
        }
        String headerOfQuestion = questionRepository.findById(id).get().getHeader();
        SocketResponse socketResponse = new SocketResponse(users,headerOfQuestion);
        return CompletableFuture.completedFuture(new ResponseEntity<SocketResponse>(socketResponse, HttpStatus.OK));
    }
}
