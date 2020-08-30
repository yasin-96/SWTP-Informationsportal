package de.thm.swtp.information_portal.controller;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import de.thm.swtp.information_portal.models.Comment.UpdateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import de.thm.swtp.information_portal.models.Comment.Comments;
import de.thm.swtp.information_portal.service.CommentService;

import static de.thm.swtp.information_portal.Util.checkUpdateCommentModel;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * @param id
     * @return
     * @throws InterruptedException
     */
    @Async
    @GetMapping("/comment/answer/{id}")
    public CompletableFuture<ResponseEntity<Comments>> findByAnswerId(@PathVariable UUID id)
            throws InterruptedException {
        return CompletableFuture.completedFuture(commentService.getCommentsByAnswerId(id.toString()));
    }

    /**
     *
     * @param updateComment
     * @param jwt
     * @return
     */
    @Async
    @PatchMapping("/comment/update")
    public CompletableFuture<ResponseEntity<Comments>> updateComment(
            @RequestBody UpdateComment updateComment,
            @AuthenticationPrincipal Jwt jwt) {

        if(checkUpdateCommentModel(updateComment)){
            var userId = jwt.getClaimAsString("sub");
            var userName = jwt.getClaimAsString("preferred_username");
            return CompletableFuture.completedFuture(commentService.update(updateComment, userId, userName));
        }
        return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.BAD_REQUEST));
    }

    /**
     * @param commentList
     * @return
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Async
    @PostMapping("/comment/new")
    public CompletableFuture<ResponseEntity<Comments>> postComments(
            @RequestBody Comments commentList,
            @AuthenticationPrincipal Jwt jwt)
            throws URISyntaxException {

        //TODO liste prüfen?
        var userId = jwt.getClaimAsString("sub");
        var userName = jwt.getClaimAsString("preferred_username");
        return CompletableFuture.completedFuture(commentService.add(commentList, userId, userName));
    }
}
