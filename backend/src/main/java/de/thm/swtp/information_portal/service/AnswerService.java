package de.thm.swtp.information_portal.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Answer.UpdateAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import static de.thm.swtp.information_portal.Util.checkUpdateAnswer;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    /**
     * @param answerList
     * @param userId
     * @param userPreferedName
     * @return
     */
    public ResponseEntity<Answers> add(Answers answerList, String userId, String userPreferedName) {

        if (answerList.getId().isEmpty()
                ||userId.isEmpty()
                ||userPreferedName.isEmpty()
                ||answerList.getListOfAnswers().isEmpty()
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }


        var answers = answerRepository.findById(answerList.getId());

        if (answers.isEmpty()) {
            var newAnswers = new Answers(
                    List.of(
                            new Answer(
                                    answerList.getListOfAnswers().get(0).getContent(),
                                    0,
                                    userId,
                                    userPreferedName
                            )
                    ),
                    answerList.getId());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(answerRepository.save(newAnswers));
        } else {
            answers.get()
                    .getListOfAnswers()
                    .add(
                            new Answer(
                                    answerList.getListOfAnswers().get(0).getContent(),
                                    0,
                                    userId,
                                    userPreferedName
                            )
                    );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(answerRepository.save(answers.get()));
        }
    }

    /**
     * @param questionId
     * @param answerId
     * @return
     */
    public ResponseEntity<Optional<Answer>> getAnswerToEdit(String questionId, String answerId) {
        var answers = answerRepository.findById(questionId);

        if (answers.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        answers.get()
                                .getListOfAnswers()
                                .stream()
                                .filter(item -> item.getId().equals(answerId))
                                .findFirst()
                );
    }


    /**
     * @param updateAnswer
     * @param userId
     * @param userName
     * @return
     */
    public ResponseEntity<Answers> updateAnswer(UpdateAnswer updateAnswer, String userId, String userName) {

        if (checkUpdateAnswer(updateAnswer)
                ||userId.isEmpty()
                ||userName.isEmpty()
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        var answersToBeModified = answerRepository.findById(updateAnswer.getId());

        if (answersToBeModified.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(null);
        }

        answersToBeModified.get().getListOfAnswers()
                .forEach(item -> {
                    if (item.getId().equals(updateAnswer.getAnswerId())) {
                        if (item.getUserId().equals(userId) && item.getUserName().equals(userName)) {

                            if (updateAnswer.getContent() != null) {
                                item.setContent(updateAnswer.getContent());
                            }

                            if (updateAnswer.getRating() != -1) {
                                item.setRating(updateAnswer.getRating());
                            }
                        }
                    }
                });

        answersToBeModified.get()
                .getListOfAnswers()
                .sort(compareByRating);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(answerRepository.save(answersToBeModified.get()));
    }

    /**
     * @param id
     * @return
     */
    public ResponseEntity<Optional<Answers>> getAnswers(String id) {

        if(id.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }


        var answers = answerRepository.findById(id);

        if (answers.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        answers.get()
                .getListOfAnswers()
                .sort(compareByRating);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(answers);
    }

    /**
     *
     */
    Comparator<Answer> compareByRating = new Comparator<Answer>() {
        @Override
        public int compare(Answer o1, Answer o2) {
            return o2.getRating() - o1.getRating();
        }
    };
}
