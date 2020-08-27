package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.service.AnswerService;
import de.thm.swtp.information_portal.service.UserService;

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
    @PostMapping("/answer")
    public CompletableFuture<ResponseEntity<Answers>> postAnswer(@RequestBody Answers answerList,
                                                                 @AuthenticationPrincipal Jwt jwt) throws URISyntaxException {
        //TODO anserlist prüfen
        var jwtSub = jwt.getClaimAsString("sub");
        return CompletableFuture.completedFuture(answerService.add(answerList, jwtSub));
    }

    /**
     * @param ids
     * @return
     */
    @Async
    @PostMapping("/answer/answerTobeEdited")
    public CompletableFuture<ResponseEntity<Answer>> getAnswerToBeEdited(@Validated @RequestBody String[] ids) {

        //TODO: IDS prüfen

        return CompletableFuture.completedFuture(answerService.getAnswerToEdit(ids));
    }

    /**
     * @param answersBody
     * @return
     * @throws URISyntaxException
     */
    @Async
    @PutMapping("/answer")
    public CompletableFuture<ResponseEntity<Answers>> editAnswer(@Validated @RequestBody Answers answersBody) throws URISyntaxException {
        //TODO objekt prüfen??
        return CompletableFuture.completedFuture(answerService.updateAnwer(answersBody));
    }

    /**
     * @param id
     * @return
     * @throws InterruptedException
     * @throws InterruptedException
     */
    @Async
    @GetMapping("/answersByQuestionId/{id}")
    public CompletableFuture<ResponseEntity<Answers>> getAnswers(@PathVariable String id) {

        //TODO ID prüfen?
        return CompletableFuture.completedFuture(answerService.getAnswers(id));
    }

    /**
     * @param answerList
     * @return
     */
    @Async
    @PostMapping("/answer/increaseRating")
    public CompletableFuture<ResponseEntity<Answers>> increaseAnswerRating(@RequestBody Answers answerList) {

        //CHECK answerlist
        return CompletableFuture.completedFuture(answerService.increaseRating(answerList));
    }
}
