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
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.ArrayList;
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
    public void shouldNotAddCommentsCommentIdIsEmptyTest() {
        var commentsCopy = comments;
        commentsCopy.setId("");
        var commentsRes = commentService.add(commentsCopy, "USER1", "USER1");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());

    }

    @Test
    public void shouldNotAddCommentsUserIdIsEmptyTest() {
        var commentsRes = commentService.add(comments, "", "USER1");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());

    }

    @Test
    public void shouldNotAddCommentsUserNameIsEmptyTest() {
        var commentsRes = commentService.add(comments, "USER1", "");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());

    }

    @Test
    public void shouldNotAddCommentsIfNoCommentIsInListTest() {

        var commentsCopy = comments;
        commentsCopy.setComments(new ArrayList());
        var commentsRes = commentService.add(comments, "USER1", "");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());
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
    public void shouldNotUpdateCommentsWithOutIdTest() {

        var updateComment = new UpdateComment("", commentsList.get(0).getId(), 120);
        var commentsRes = commentService.update(updateComment, "USER1", "USER1");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(304, commentsRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateCommentsWithOutUserIdTest() {

        var updateComment = new UpdateComment("Answer1", commentsList.get(0).getId(), 120);
        var commentsRes = commentService.update(updateComment, "", "USER1");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateCommentsWithOutUserNameTest() {

        var updateComment = new UpdateComment("Answer1", commentsList.get(0).getId(), 120);
        var commentsRes = commentService.update(updateComment, "USER1", "");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateCommentsWithOutComment() {

        var commentsRes = commentService.update(new UpdateComment("", "", 0), "USER1", "");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateCommentsWithOutCommentData() {

        var commentsRes = commentService.update(new UpdateComment("Answer1", "", 0), "USER1", "");
        var commentsRes2 = commentService.update(new UpdateComment("", commentsList.get(0).getId(), 0), "USER1", "");
        var commentsRes3 = commentService.update(new UpdateComment(null, null, 0), "USER1", "");
        var commentsRes4 = commentService.update(new UpdateComment("Answer1", null, 0), "USER1", "");
        var commentsRes5 = commentService.update(new UpdateComment(null, commentsList.get(0).getId(), 0), "USER1", "");
        var commentsRes6 = commentService.update(new UpdateComment(null, null, null), "USER1", "");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());

        Assertions.assertNotNull(commentsRes2);
        Assertions.assertNull(commentsRes2.getBody());
        Assertions.assertEquals(400, commentsRes2.getStatusCodeValue());

        Assertions.assertNotNull(commentsRes3);
        Assertions.assertNull(commentsRes3.getBody());
        Assertions.assertEquals(400, commentsRes3.getStatusCodeValue());

        Assertions.assertNotNull(commentsRes4);
        Assertions.assertNull(commentsRes4.getBody());
        Assertions.assertEquals(400, commentsRes4.getStatusCodeValue());

        Assertions.assertNotNull(commentsRes5);
        Assertions.assertNull(commentsRes5.getBody());
        Assertions.assertEquals(400, commentsRes5.getStatusCodeValue());

        Assertions.assertNotNull(commentsRes6);
        Assertions.assertNull(commentsRes6.getBody());
        Assertions.assertEquals(400, commentsRes6.getStatusCodeValue());


    }

    @Test
    public void shouldFindCommentsByAnswerIdTest() {

        var commentsRes = commentService.getCommentsByAnswerId("Answer1");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertTrue(commentsRes.hasBody());
        Assertions.assertEquals(200, commentsRes.getStatusCodeValue());

    }

    @Test
    public void shouldNotFindCommentsByAnswerIdTest() {

        var commentsRes = commentService.getCommentsByAnswerId("");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(400, commentsRes.getStatusCodeValue());

    }

    @Test
    @AfterTestClass
    public void shouldNotFindCommentsByAnswerIdEmptyListTest() {

        commentRepository.deleteAll();
        var commentsRes = commentService.getCommentsByAnswerId("Answer1");

        Assertions.assertNotNull(commentsRes);
        Assertions.assertNull(commentsRes.getBody());
        Assertions.assertEquals(404, commentsRes.getStatusCodeValue());

    }
}
