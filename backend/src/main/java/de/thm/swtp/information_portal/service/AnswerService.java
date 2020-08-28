package de.thm.swtp.information_portal.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.repositories.AnswerRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;



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
    public ResponseEntity<Answers> add(Answers answerList, String jwtSub) throws URISyntaxException {
        var answers = this.findByQuestionId(answerList.getId());

        if (answers.isEmpty()) {
            var newAnswers = new Answers(
                    List.of(
                        new Answer(
                            answerList.getListOfAnswers().get(0).getContent(),
                            jwtSub,
                            0
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
                    jwtSub,
                    0
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

    public ResponseEntity<Answer> getAnswerToEdit(String[] ids){
        var answers = this.findByQuestionId(ids[0]);
        var answerList = answers.get().getListOfAnswers();


        //TODO: brauchen wir garnicht entweder wir finden was und returnen das oder geben nichts zurück
        var foundAnswer = new Answer();

        for (var answer : answerList) {
            if (answer.getId().equals(ids[1])) {
                foundAnswer = answer;

            }
        }

        return new ResponseEntity<Answer>(foundAnswer, HttpStatus.OK);
    }



    public ResponseEntity<Answers> updateAnwer(Answers answersBody) throws URISyntaxException {
        var answersToBeModified = this.findByQuestionId(answersBody.getId());
        var modifiedAnswer = answersBody.getListOfAnswers().get(0);
        var listOfAnswers = answersToBeModified.get().getListOfAnswers();


        listOfAnswers.forEach((item -> {
            if (item.getId().equals(modifiedAnswer.getId())) {
                listOfAnswers.set(listOfAnswers.indexOf(item), modifiedAnswer);
            }
        }));

        answersToBeModified.get().setListOfAnswers(listOfAnswers);

        this.postAnswer(answersToBeModified.get());

        return ResponseEntity
                .created(new URI("/api/answer/" + answersToBeModified.get().getId())).body(answersToBeModified.get());
    }




    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<Answers> getAnswers(String id) {

        var answers = this.findByQuestionId(id);

        if (answers.isPresent()) {
            var allAnswers = answers.get().getListOfAnswers();

            //write userName to structure
            allAnswers.forEach( aData -> {
                if (aData.getUserId() != null) {
                    var user = userRepository.findById(aData.getUserId());
                    if(user.isPresent()){
                        var userName = user.get().getPreferred_username();
                        aData.setUserName(!userName.isEmpty() ? userName : "Unknown");
                    }
                } else {
                    aData.setUserName("Unknown");
                }
            });

            //wird sortiert aber dann nicht mehr benutzt
            //allAnswers.sort(compareByRating);
        }
        //TODO warum geben wir eigentlich hier answers zurück
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
