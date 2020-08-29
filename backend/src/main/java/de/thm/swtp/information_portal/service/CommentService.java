package de.thm.swtp.information_portal.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.models.Comment;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.repositories.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * @return
     */
    public List<Comments> findAllComments() {
        return commentRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public Optional<Comments> findByAnswerId(String id) {
        return commentRepository.findById(id);
    }

    /**
     * @param comments
     * @return
     */
    public Comments postComments(Comments comments) {
        return commentRepository.save(comments);
    }

    /**
     *
     * @param commentList
     * @param userId
     * @return
     * @throws URISyntaxException
     */
    public ResponseEntity<Comments> add(Comments commentList, String userId, String userName) throws URISyntaxException {
        var comments = this.findByAnswerId(commentList.getId());
        var existingComment = commentList.getComments().get(0);
        if (!comments.isPresent()) {
            var newComments = new Comments(
                    List.of(
                            new Comment(
                                    commentList.getComments().get(0).getContent(),
                                    userId,
                                    userName,
                                    commentList.getComments().get(0).getRating()
                            )
                    ),
                    commentList.getId()
            );
            this.postComments(newComments);

            return ResponseEntity.created(new URI("/api/answer" + newComments.getId())).body(newComments);
        } else {
            var commentsPresent = comments.get().getComments();
            var newComment = new Comment(
                    existingComment.getContent(),
                    userId,
                    userName,
                    existingComment.getRating()
            );
            //TODO add und set ? brauchen wir hier beides
            commentsPresent.add(newComment);
            comments.get().setComments(commentsPresent);

            this.postComments(comments.get());
            return ResponseEntity.created(new URI("/api/answer" + comments.get().getId())).body(comments.get());
        }
    }


    /**
     *
     * @param answerId
     * @return
     */
    public ResponseEntity<Comments> getCommentsByAnswerId(String answerId) {
        var comments = this.findByAnswerId(answerId);
        if (comments.isPresent()) {
            comments.get()
                    .getComments()
                    .sort(compareByRating);
        }
        return comments
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    /**
     *
     * @param commentList
     * @return
     */
    public ResponseEntity<Comments> increaseRating(Comments commentList) {
        var commentsToBeModified = this.findByAnswerId(commentList.getId());
        var modifiedComment = commentList.getComments().get(0);

        var listOfComments = commentsToBeModified.get().getComments();

        listOfComments.forEach((item -> {
            if (item.getId().equals(modifiedComment.getId())) {
                listOfComments.set(listOfComments.indexOf(item), modifiedComment);
            }
        }));

        //change data in list
        commentsToBeModified.get().setComments(listOfComments);

        //TODO PRÜFEN ob geklappt hat?
        this.postComments(commentsToBeModified.get());

        return commentsToBeModified
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    /**
     *
     */
    Comparator<Comment> compareByRating = new Comparator<Comment>() {
        @Override
        public int compare(Comment o1, Comment o2) {
            return o2.getRating() - o1.getRating();
        }
    };

}
