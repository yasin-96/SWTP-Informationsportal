package de.thm.swtp.information_portal.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import de.thm.swtp.information_portal.models.Comment.Comment;
import de.thm.swtp.information_portal.models.Comment.UpdateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.thm.swtp.information_portal.models.Comment.Comments;
import de.thm.swtp.information_portal.repositories.CommentRepository;

import static de.thm.swtp.information_portal.Util.checkUpdateComment;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * @param commentList
     * @param userId
     * @return
     */
    public ResponseEntity<Comments> add(Comments commentList, String userId, String userName) {

        if (commentList.getId().isEmpty()
                ||userId.isEmpty()
                ||userName.isEmpty()
                ||commentList.getComments().isEmpty()
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        var comments = commentRepository.findById(commentList.getId());
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

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentRepository.save(newComments));
        } else {
            comments.get()
                    .getComments()
                    .add(
                            new Comment(
                                    existingComment.getContent(),
                                    userId,
                                    userName,
                                    existingComment.getRating()
                            )
                    );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentRepository.save(comments.get()));
        }
    }


    /**
     * @param answerId
     * @return
     */
    public ResponseEntity<Optional<Comments>> getCommentsByAnswerId(String answerId) {

        if (answerId.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        var comments = commentRepository.findById(answerId);

        if (comments.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        comments.get()
                .getComments()
                .sort(compareByRating);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }

    /**
     * @param updateComment
     * @param userId
     * @param userName
     * @return
     */
    public ResponseEntity<Comments> update(UpdateComment updateComment, String userId, String userName) {

        if (checkUpdateComment(updateComment)
                ||userId.isEmpty()
                ||userName.isEmpty()
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        var commentsToBeModified = commentRepository.findById(updateComment.getId());

        if (commentsToBeModified.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(null);
        }

        commentsToBeModified.get().getComments()
                .forEach(item -> {
                    if (item.getId().equals(updateComment.getCommentId())) {
                        if (item.getUserId().equals(userId) && item.getUserName().equals(userName)) {

                            if (updateComment.getContent() != null) {
                                item.setContent(updateComment.getContent());
                            }

                            if (updateComment.getRating() != -1) {
                                item.setRating(updateComment.getRating());
                            }
                        }
                    }
                });

        commentsToBeModified.get()
                .getComments()
                .sort(compareByRating);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentRepository.save(commentsToBeModified.get()));
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
