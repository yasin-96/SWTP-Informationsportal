package de.thm.swtp.information_portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.thm.swtp.information_portal.models.*;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.CommentRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
public class SocketService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public ResponseEntity<SocketResponse> createMessage(String wsMessage, String userId, String userName){

        // init data
        var wsData = new SocketReceived();
        var socketResponse = new SocketResponse();
        var users = new HashSet<User>();

        try {

            // parse jsObject to java object
            var parseJsObject = new ObjectMapper();
            wsData = parseJsObject.readValue(wsMessage, SocketReceived.class);

            var headerOfQuestion = questionRepository.findById(wsData.getQuestionId()).get().getHeader();

            if (wsData.getIsAnswer()) {
                var answers = answerRepository.findById(wsData.getQuestionId());

                var answersOfQuestion = answers.get().getListOfAnswers();
                for (Answer answer : answersOfQuestion) {
                    userRepository
                            .findById(answer.getUserId())
                            .ifPresent(users::add);
                }


                socketResponse = new SocketResponse(
                        wsData.getQuestionId(),
                        users,
                        headerOfQuestion,
                        wsData.getIsAnswer(),
                        wsData.getIsComment(),
                        new MinimalUser(userId, userName)
                );
            }

            if (wsData.getIsComment()) {

                var comments = commentRepository.findById(wsData.getAnswerId());
                var allComments = comments.get().getComments();
                for(Comment comment: allComments){
                    userRepository
                            .findById(comment.getUserId())
                            .ifPresent(users::add);
                }

                socketResponse = new SocketResponse(
                        wsData.getQuestionId(),
                        wsData.getAnswerId(),
                        users,headerOfQuestion,
                        wsData.getIsAnswer(),
                        wsData.getIsComment(),
                        new MinimalUser(userId, userName)
                );

            }

        } catch (Exception e) {
            //Loggen vielleicht
            e.printStackTrace();
        }

        //TODO wenn socketResponse leer ist?
        return new ResponseEntity(socketResponse, HttpStatus.OK);

    }
}
