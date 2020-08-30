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

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    /**
     * @param answerList
     * @return
     */
    public Answers postAnswer(Answers answerList) {
        return answerRepository.save(answerList);
    }

    /**
     *
     * @param answerList
     * @return
     * @throws URISyntaxException
     */
    public ResponseEntity<Answers> add(Answers answerList, String userId, String userPreferedName) throws URISyntaxException {
        var answers = this.findByQuestionId(answerList.getId());

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

            //Prüfen?? ob es geklappt hat
            this.postAnswer(newAnswers);

            return ResponseEntity.created(new URI("/api/answer" + newAnswers.getId())).body(newAnswers);
        } else {
            var answersPresent = answers.get().getListOfAnswers();
            var newAnswer = new Answer(
                    answerList.getListOfAnswers().get(0).getContent(),
                    0,
                    userId,
                    userPreferedName
            );
            answersPresent.add(newAnswer);

            //Prüfen?? ob es geklappt hat
            this.postAnswer(answers.get());

            return ResponseEntity.created(new URI("/api/answer" + answers.get().getId())).body(answers.get());
        }
    }

    /**
     * @param id
     * @return
     */
    public Optional<Answers> findByQuestionId(String id) {

        return answerRepository.findById(id);
    }

    public ResponseEntity<Answer> getAnswerToEdit(String questionId, String answerId){
        var answers = answerRepository.findById(questionId);

        if(answers.isPresent()){
            var foundAnswer = answers.get()
                    .getListOfAnswers()
                    .stream()
                    .filter(item -> item.getId().equals(answerId))
                    .findFirst();

            return new ResponseEntity(foundAnswer, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }



    public ResponseEntity<Answers> updateAnswer(UpdateAnswer updateAnswer, String userId, String userName) throws URISyntaxException {
        var answersToBeModified = this.findByQuestionId(updateAnswer.getId());

        answersToBeModified.get().getListOfAnswers()
                .stream()
                .forEach(item -> {
                  if(item.getId().equals(updateAnswer.getAnswerId())){
                      if(item.getUserId().equals(userId) && item.getUserName().equals(userName)){

                          if(updateAnswer.getContent() != null){
                              item.setContent(updateAnswer.getContent());
                          }

                          if(updateAnswer.getRating() != -1){
                              item.setRating(updateAnswer.getRating());
                          }


                      }
                  }
                });
        answersToBeModified.get().getListOfAnswers().sort(compareByRating);
        this.postAnswer(answersToBeModified.get());

        return new ResponseEntity(answersToBeModified.get(), HttpStatus.OK);
                //.created(new URI("/api/answer/" + answersToBeModified.get().getId()))
    }


    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<Answers> getAnswers(String id) {

        var answers = this.findByQuestionId(id);

        if (answers.isPresent()) {
            answers.get()
                .getListOfAnswers()
                .sort(compareByRating);
        }
        return answers
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }



    /**
     *
     * @param answerList
     * @return
     */
    public ResponseEntity<Answers> increaseRating(Answers answerList){

        var answersToBeModified = this.findByQuestionId(answerList.getId());
        var modifiedAnswer = answerList.getListOfAnswers().get(0);
        var listOfAnswers = answersToBeModified.get().getListOfAnswers();

        listOfAnswers.forEach((item -> {
            if (item.getId().equals(modifiedAnswer.getId())) {
                listOfAnswers.set(listOfAnswers.indexOf(item), modifiedAnswer);
            }
        }));

        answersToBeModified.get().setListOfAnswers(listOfAnswers);

        //TODO das prüfen??
        var responseList = this.postAnswer(answersToBeModified.get());

        return answersToBeModified
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    Comparator<Answer> compareByRating = new Comparator<Answer>() {
        @Override
        public int compare(Answer o1, Answer o2) {
            return o2.getRating() - o1.getRating();
        }
    };
}
