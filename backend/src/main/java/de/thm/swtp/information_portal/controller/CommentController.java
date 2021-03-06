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

import javax.swing.text.html.Option;

import static de.thm.swtp.information_portal.Util.checkUpdateCommentModel;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Find all comments by answer id
     * @param id Id of answer
     * @return found comments or null
     */
    @Async
    @GetMapping("/comment/answer/{id}")
    public CompletableFuture<ResponseEntity<Optional<Comments>>> findByAnswerId(@PathVariable UUID id) {
        return CompletableFuture.completedFuture(commentService.getCommentsByAnswerId(id.toString()));
    }

    /**
     * The transferred data of the comment are renewed
     * @param updateComment
     * @param jwt User access credentials
     * @return all comments
     */
    @Async
    @PatchMapping("/comment/update")
    public CompletableFuture<ResponseEntity<Comments>> updateComment(
            @RequestBody UpdateComment updateComment,
            @AuthenticationPrincipal Jwt jwt) {

        if(jwt == null) {
            return CompletableFuture.completedFuture(
                    ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(null)
            );
        }

        if(checkUpdateCommentModel(updateComment)){
            var userId = jwt.getClaimAsString("sub");
            var userName = jwt.getClaimAsString("preferred_username");
            return CompletableFuture.completedFuture(commentService.update(updateComment, userId, userName));
        }

        return CompletableFuture.completedFuture(
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(null)
        );
    }

    /**
     * Create new comment to a specific answer
     * @param commentList The new comment
     * @param jwt User access credentials
     * @return all comments for a specific answer
     */
    @Async
    @PostMapping("/comment/new")
    public CompletableFuture<ResponseEntity<Comments>> postComments(
            @RequestBody Comments commentList,
            @AuthenticationPrincipal Jwt jwt) {

        if(jwt == null) {
            return CompletableFuture.completedFuture(
                    ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(null)
            );
        }

        var userId = jwt.getClaimAsString("sub");
        var userName = jwt.getClaimAsString("preferred_username");
        return CompletableFuture.completedFuture(commentService.add(commentList, userId, userName));
    }
}
