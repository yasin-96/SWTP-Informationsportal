package de.thm.swtp.information_portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Comment.Comment;
import de.thm.swtp.information_portal.models.Socket.SocketReceived;
import de.thm.swtp.information_portal.models.Socket.SocketResponse;
import de.thm.swtp.information_portal.models.User.User;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.CommentRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;

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

    public ResponseEntity<SocketResponse> createMessage(String wsMessage){

        // init data
        var wsData = new SocketReceived();
        SocketResponse socketResponse = null;
        var users = new HashSet<User>();

        try {

            // parse jsObject to java object
            var parseJsObject = new ObjectMapper();
            wsData = parseJsObject.readValue(wsMessage, SocketReceived.class);

            var question = questionRepository.findById(wsData.getQuestionId());
            if(question.isPresent()){
                var headerOfQuestion = question.get().getHeader();

                if (wsData.getIsAnswer()) {
                    var answers = answerRepository.findById(wsData.getQuestionId());

                    if(answers.isPresent()) {
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
                                wsData.getMinimalUser()
                        );
                    }

                }

                if (wsData.getIsComment()) {

                    var comments = commentRepository.findById(wsData.getAnswerId());
                    if(comments.isPresent()){
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
                                wsData.getMinimalUser()
                        );
                    }
                }
            }

        } catch (Exception e) {
            //Loggen vielleicht
            e.printStackTrace();
        }

        if(socketResponse != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(socketResponse);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);

    }
}
