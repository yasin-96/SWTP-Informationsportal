package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.UserInformation;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
    /**
     *
     * @param userId
     * @return
     */
    public ResponseEntity<UserInformation> getUserInfo(String userId) throws URISyntaxException {

        var userInfo = userInformationRepository.findById(userId);

        if(userInfo.isPresent()){
            return  userInfo
                    .map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
            var allQuestions = questionRepository.findAll();
            var questionsOfUser = new ArrayList<>();

            if(!allQuestions.isEmpty()){

                var numberOfQuestions = Math.toIntExact(allQuestions.stream()
                        .filter(quest -> quest.getUserId().equals(userId))
                        .count()
                );

                /*for(Question question: allQuestions){
                    if(question.getUserId().equals(userId)){
                        questionsOfUser.add(question);
                    }
                }*/

                int numberOfAnswers = this.getNumberOfAnswers(userId);

                var newUserInfo = new UserInformation(
                        userId,
                        numberOfQuestions,
                        numberOfAnswers
                );

                this.addUserInfo(newUserInfo);

                return ResponseEntity
                        .created(new URI("/info-portal/api" + newUserInfo.getId()))
                        .body(newUserInfo);
            }

        return ResponseEntity
                .created(new URI("/info-portal/api" + userId))
                .body(new UserInformation(userId, 0, 0));
    }

    /**
     *
     * @param userInfo
     * @return
     */
    public UserInformation addUserInfo(UserInformation userInfo){
       return userInformationRepository.save(userInfo);
    }

    /**
     *
     * @param id
     * @return
     */
    int getNumberOfAnswers(String id) {
        var allAnswers = answerRepository.findAll();

        if(!allAnswers.isEmpty()){
            var numberOfAllAnswers = Math.toIntExact(
                    allAnswers.stream()
                    .map(listOfAllAnswers -> listOfAllAnswers.getListOfAnswers())
                    .map(list-> list.stream().filter(answer -> answer.getId().equals(id)))
                    .collect(Collectors.toList())
                            .stream()
                            .count()
            );
            return numberOfAllAnswers;
        }
        return 0;
    }
}
