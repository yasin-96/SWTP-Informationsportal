package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.Comment.Comment;
import de.thm.swtp.information_portal.models.Comment.Comments;
import de.thm.swtp.information_portal.models.Comment.UpdateComment;
import de.thm.swtp.information_portal.repositories.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ComponentScan()
@DataMongoTest
@ContextConfiguration
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    private Comments comments;

    private List<Comment> commentsList;


    @BeforeEach
    public void Setup() {
        commentRepository.deleteAll();

        commentsList = List.of(
                new Comment("Comment1", "USER1", "USER1", 10),
                new Comment("Comment2", "USER2", "USER2", 20)
        );

        comments = new Comments(commentsList, "Answer1");
        commentRepository.save(comments);
    }

    @Test
    public void shouldAddCommentsTest() {
        var commentsRes = commentService.add(comments, "USER1", "USER1");

        Assertions.assertNotNull(commentsRes, "Comment added Successfully");
        Assertions.assertNotNull(commentsRes);
        Assertions.assertTrue(commentsRes.hasBody());
        Assertions.assertEquals(200, commentsRes.getStatusCodeValue());

    }

    @Test
    public void shouldUpdateCommentsTest() {

        var updateComment = new UpdateComment("Answer1", commentsList.get(0).getId(), 120);
        var commentsRes = commentService.update(updateComment, "USER1", "USER1");

        Assertions.assertNotNull(commentsRes, "Should Successfully update comments");
        Assertions.assertTrue(commentsRes.hasBody());
        Assertions.assertEquals(200, commentsRes.getStatusCodeValue());
    }

    @Test
    public void shouldFindCommentsByAnswerIdTest() {

        var commentsRes = commentService.getCommentsByAnswerId("Answer1");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertTrue(commentsRes.hasBody());
        Assertions.assertEquals(200, commentsRes.getStatusCodeValue());

    }
}
