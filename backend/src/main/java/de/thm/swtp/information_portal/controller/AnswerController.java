package de.thm.swtp.information_portal.controller;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import de.thm.swtp.information_portal.models.Answer.UpdateAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import de.thm.swtp.information_portal.models.Answer.Answer;
import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.service.AnswerService;

import static de.thm.swtp.information_portal.Util.checkUpdateAnswerModel;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * Creates an answer to a question based on the transferred data
     * @param answerList New Answer
     * @param jwt User access credentials
     * @return List of all Answers for a question
     */
    @Async
    @PostMapping("/answer/new")
    public CompletableFuture<ResponseEntity<Answers>> postAnswer(
            @RequestBody Answers answerList,
            @AuthenticationPrincipal Jwt jwt) {

        if(jwt == null) {
            return CompletableFuture.completedFuture(
                    ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(null)
            );
        }

        var userId = jwt.getClaimAsString("sub");
        var userPreferedName = jwt.getClaimAsString("preferred_username");
        return CompletableFuture.completedFuture(answerService.add(answerList, userId, userPreferedName));
    }

    /**
     * Find the one answer to edit the information
     * @param qId Id from Question
     * @param aId Id from Answer
     * @return
     */
    @Async
    @GetMapping("/answer/edit")
    public CompletableFuture<ResponseEntity<Optional<Answer>>> getAnswerToBeEdited(
            @RequestParam UUID qId, @RequestParam UUID aId) {
        return CompletableFuture.completedFuture(
                answerService.getAnswerToEdit(qId.toString(), aId.toString())
        );
    }

    /**
     * The transferred data of the answer are renewed
     * @param updateAnswer Answer with new data
     * @param jwt User access credentials
     * @return
     */
    @PatchMapping("/answer/update")
    public CompletableFuture<ResponseEntity<Answers>> updateContent(
            @RequestBody UpdateAnswer updateAnswer,
            @AuthenticationPrincipal Jwt jwt){

        if(jwt == null) {
            return CompletableFuture.completedFuture(
                    ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(null)
            );
        }

        if (checkUpdateAnswerModel(updateAnswer)) {
            var userId = jwt.getClaimAsString("sub");
            var userPreferedName = jwt.getClaimAsString("preferred_username");
            return CompletableFuture.completedFuture(answerService.updateAnswer(updateAnswer, userId, userPreferedName));
        }

        return CompletableFuture.completedFuture(
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(null)
        );
    }

    /**
     * Find all answers based on the question id
     * @param id Id of question
     * @return All answer to the question or null
     */
    @Async
    @GetMapping("/answer/question/{id}")
    public CompletableFuture<ResponseEntity<Optional<Answers>>> getAnswers(@PathVariable UUID id) {
        return CompletableFuture.completedFuture(answerService.getAnswers(id.toString()));
    }
}
