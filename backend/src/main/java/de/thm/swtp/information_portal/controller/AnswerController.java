package de.thm.swtp.information_portal.controller;

import java.net.URISyntaxException;
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
     * @param answerList
     * @return
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Async
    @PostMapping("/answer/new")
    public CompletableFuture<ResponseEntity<Answers>> postAnswer(@RequestBody Answers answerList,
                                                                 @AuthenticationPrincipal Jwt jwt) throws URISyntaxException {
        //TODO anserlist prüfen
        var userId = jwt.getClaimAsString("sub");
        var userPreferedName = jwt.getClaimAsString("preferred_username");
        return CompletableFuture.completedFuture(answerService.add(answerList, userId, userPreferedName));
    }

    /**
     * @param
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

    @PatchMapping("/answer/update")
    public CompletableFuture<ResponseEntity<Answers>> updateContent(@RequestBody UpdateAnswer updateAnswer,
                                                                    @AuthenticationPrincipal Jwt jwt) throws URISyntaxException {
        if (checkUpdateAnswerModel(updateAnswer)) {
            var userId = jwt.getClaimAsString("sub");
            var userPreferedName = jwt.getClaimAsString("preferred_username");
            return CompletableFuture.completedFuture(answerService.updateAnswer(updateAnswer, userId, userPreferedName));
        }

        return CompletableFuture.completedFuture(
                new ResponseEntity(HttpStatus.BAD_REQUEST)
        );
    }

    /**
     * @param id
     * @return
     * @throws InterruptedException
     * @throws InterruptedException
     */
    @Async
    @GetMapping("/answer/question/{id}")
    public CompletableFuture<ResponseEntity<Optional<Answers>>> getAnswers(@PathVariable UUID id) {
        return CompletableFuture.completedFuture(answerService.getAnswers(id.toString()));
    }
}
