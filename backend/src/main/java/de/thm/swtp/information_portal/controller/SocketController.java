package de.thm.swtp.information_portal.controller;

import com.mongodb.util.Hash;
import de.thm.swtp.information_portal.models.*;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.CommentRepository;
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

    @Autowired
    private CommentRepository commentRepository;

    @MessageMapping("/hello")
    @SendTo("/notify")
    public CompletableFuture<ResponseEntity<SocketResponse>> socketResponse(@RequestBody String wsMessage) {

        // init data
        SocketReceived wsData = null;
        SocketResponse socketResponse = null;
        var users = new HashSet<User>();
        String headerOfQuestion = questionRepository.findById(wsData.getQuestionId()).get().getHeader();

        try {

            // parse jsObject to java object
            var parseJsObject = new ObjectMapper();
            wsData = parseJsObject.readValue(wsMessage, SocketReceived.class);

            if (wsData.getIsAnswer()) {
                Optional<Answers> answers = answerRepository.findById(wsData.getQuestionId());


                List<Answer> answersOfQuestion = answers.get().getListOfAnswers();
                for (Answer answer : answersOfQuestion) {
                    Optional<User> userToBeAdded = userRepository.findById(answer.getUserId());
                    users.add(userToBeAdded.get());
                }


                socketResponse = new SocketResponse(wsData.getQuestionId(), users, headerOfQuestion,
                        wsData.getIsAnswer(), wsData.getIsComment(), wsData.getMinimalUser());
            }

            if (wsData.getIsComment()) {

                Optional<Comments> comments = commentRepository.findById(wsData.getAnswerId());
                List<Comment> allComments = comments.get().getComments();
                for(Comment comment: allComments){
                    Optional<User> userToBeAdded = userRepository.findById(comment.getId());
                    users.add(userToBeAdded.get());
                }

                socketResponse = new SocketResponse(wsData.getQuestionId(),wsData.getAnswerId(),users,headerOfQuestion,
                        wsData.getIsAnswer(),wsData.getIsComment(),wsData.getMinimalUser());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(new ResponseEntity<SocketResponse>(socketResponse, HttpStatus.OK));
    }
}
