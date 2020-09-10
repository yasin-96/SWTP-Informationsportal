package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.User.UserInformation;
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

        if(userId == null || userId.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }


        var allQuestions = questionRepository.findAll();
        int numberOfQuestions = 0;
        int numberOfAnswers = 0;

        if(!allQuestions.isEmpty()) {
            numberOfQuestions = Math.toIntExact(allQuestions.stream()
                    .filter(quest ->
                            quest.getUserId().equals(userId)
                    )
                    .count()
            );
        }

        numberOfAnswers = this.getNumberOfAnswers(userId);

        var newUserInfo = new UserInformation(
                userId,
                numberOfQuestions,
                numberOfAnswers
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userInformationRepository.save(newUserInfo));
    }

    /**
     *
     * @param id
     * @return
     */
    int getNumberOfAnswers(String id) {
        var allAnswers = answerRepository.findAll();

        //TODO: hier wird nicht richtig gezählt
        if(!allAnswers.isEmpty()){

            return Math.toIntExact(
                    allAnswers.stream()
                    .map(listOfAllAnswers -> listOfAllAnswers.getListOfAnswers())
                    .flatMap(Collection::stream)
                    .map( answer -> answer.getUserId())
                    .filter( aId -> aId.contains(id))
                    .map(x -> x.contains(id))
                    .count()
            );
        }
        return 0;
    }
}
