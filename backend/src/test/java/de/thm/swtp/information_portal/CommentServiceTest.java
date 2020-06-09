package de.thm.swtp.information_portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import de.thm.swtp.information_portal.models.Comment;
import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.service.CommentService;

@ComponentScan(basePackages={"de.thm.swtp.information_portal"})
@DataMongoTest
class CommentServiceTest {

	@Autowired
	private CommentService commentService;

	@Test
	public void shouldPostCommentsTest() {

		List<Comment> commentsList= new ArrayList<Comment>();

		final Comment commentOne = new Comment("Comment1", "USER1" , 10);		
		final Comment commentTwo = new Comment("Comment2", "USER2" , 20);

		commentsList.add(commentOne);
		commentsList.add(commentTwo);

		Comments commentsRes =  commentService.postComments(new Comments(commentsList, "1S"));
		Assertions.assertNotNull(commentsRes ,"Successfully Post a Comment");	

	}

	@Test
	public void shouldFindAllCommentsTest() {

		List<Comments> commentRes =  commentService.findAllComments();
		
		Assertions.assertNotNull(commentRes ,"Should Successfully find all comments");	
		Assertions.assertTrue(!commentRes.isEmpty());
		Assertions.assertEquals("Comment1", commentRes.get(0).getComments().get(0).getContent());
	}
	
	@Test
	public void shouldFindByAnswerIdTest() {

		Optional<Comments> commentRes = commentService.findByAnswerId("1S");
		
		Assertions.assertNotNull(commentRes);	
		Assertions.assertTrue(commentRes.isPresent());
		Assertions.assertEquals("Comment1", commentRes.get().getComments().get(0).getContent() );
		Assertions.assertEquals(10, commentRes.get().getComments().get(0).getRating() );	
	}

}
