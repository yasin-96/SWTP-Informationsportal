package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Comment;
import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.service.CommentService;
import de.thm.swtp.information_portal.service.UserService;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    /**
     * @return
     * @throws InterruptedException
     */
    @Async
    @GetMapping("/allComments")
    public CompletableFuture<List<Comments>> findAllComments() throws InterruptedException {
        return CompletableFuture.completedFuture(commentService.findAllComments());
    }

    /**
     * @param id
     * @return
     * @throws InterruptedException
     */
    @Async
    @GetMapping("/commentsByAnswerId/{id}")
    public CompletableFuture<ResponseEntity<Comments>> findByAnswerId(@PathVariable String id)
            throws InterruptedException {
        //TODO id prüfen oder Springboot es überlassen?!
        return CompletableFuture.completedFuture(commentService.getCommentsByAnswerId(id));
    }

    /**
     * @param commentList
     * @return
     */
    @Async
    @PostMapping("/comment/increaseRating")
    public CompletableFuture<ResponseEntity<Comments>> increaseCommentRating(
            @Validated @RequestBody Comments commentList) {
        //TODO liste validieren?
        return CompletableFuture.completedFuture(commentService.increaseRating(commentList));
    }

    /**
     * @param commentList
     * @return
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Async
    @PostMapping("/newComments")
    public CompletableFuture<ResponseEntity<Comments>> postComments(@RequestBody Comments commentList, @AuthenticationPrincipal Jwt jwt)
            throws URISyntaxException {
        //TODO liste prüfen?
        var jwtSub = jwt.getClaimAsString("sub");
        return CompletableFuture.completedFuture(commentService.add(commentList, jwtSub));
    }
}
