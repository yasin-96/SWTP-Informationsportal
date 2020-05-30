package de.thm.swtp.information_portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import de.thm.swtp.information_portal.models.Comment;
import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.repositories.CommentRepository;

@DataMongoTest
class CommentRepositoryTest {

	private static final Logger LOGGER=LoggerFactory.getLogger(CommentRepositoryTest.class);

	@Autowired
	private CommentRepository commentRepository;
	
	@BeforeEach
	public void setUp() {
		commentRepository.deleteAll();
	}
	
	@Test
	public void shouldInsertCommentsTest() {

		LOGGER.info("\n\n **********Start InsertComments Test********** \n\n");
		
		List<Comment> commentsList= new ArrayList<Comment>();
		
		final Comment commentOne = new Comment("Comment1", "USER1" , 10);		
		final Comment commentTwo = new Comment("Comment2", "USER2" , 20);
		
		commentsList.add(commentOne);
		commentsList.add(commentTwo);
		
		Comments comments = new Comments(commentsList, "2S");
		Comments commentRes = commentRepository.save(comments);
		Assertions.assertEquals(comments.getId(), commentRes.getId(), "Successfully added a comments");

		LOGGER.info("\n\n **********End InsertComments Test********** \n\n");
	}
	
	
	@Test
	public void shouldGetCommentByIdTest() {

		LOGGER.info("\n\n **********Start getCommentById Test********** \n\n");
		
		List<Comment> commentsList= new ArrayList<Comment>();
		
		final Comment commentOne = new Comment("Comment1", "USER1" , 10);		
		final Comment commentTwo = new Comment("Comment2", "USER2" , 20);
		
		commentsList.add(commentOne);
		commentsList.add(commentTwo);
		
		Comments comments = new Comments(commentsList, "2S");
		commentRepository.save(comments);
		
		Assertions.assertEquals("2S" , commentRepository.findById("2S").get().getId(),
				"Successfully fetched a comments by Answer ID");
		
		LOGGER.info("\n\n **********End getCommentById Test********** \n\n");
	}
	
	@Test
	public void shouldUpdateCommentsTest() {

		LOGGER.info("\n\n **********Start updateComments Test********** \n\n");
		
		List<Comment> commentsList= new ArrayList<Comment>();
		
		final Comment commentOne = new Comment("Comment1", "USER1" , 10);		
		final Comment commentTwo = new Comment("Comment2", "USER2" , 20);
		
		commentsList.add(commentOne);
		commentsList.add(commentTwo);
		
		Comments comments = new Comments(commentsList, "1S");
		commentRepository.save(comments);

		commentTwo.setContent("Comment3");
		commentTwo.setRating(12);
		Assertions.assertEquals(commentTwo.getContent(), commentRepository.save(comments).getComments().get(1).getContent(),
				"Successfully updated a comments");
		
		LOGGER.info("\n\n **********End updateComments Test********** \n\n");
	}
	
	@Test
	public void shouldGetAllCommentsTest() {

		LOGGER.info("\n\n **********Start getAllComments Test********** \n\n");
		
		List<Comment> commentsList= new ArrayList<Comment>();
		
		final Comment commentOne = new Comment("Comment1", "USER1" , 10);		
		final Comment commentTwo = new Comment("Comment2", "USER2" , 20);
		
		commentsList.add(commentOne);
		commentsList.add(commentTwo);
		
		Comments comments = new Comments(commentsList, "1S");
		commentRepository.save(comments);

		Assertions.assertEquals(1, commentRepository.findAll().size(),
				"Successfully fetched the list a comments");
		
		LOGGER.info("\n\n **********End getAllComments Test********** \n\n");
	}
	
	@Test
	public void shouldDeleteCommentsTest() {

		LOGGER.info("\n\n **********Start deleteComments Test********** \n\n");
		
		List<Comment> commentsList= new ArrayList<Comment>();
		
		final Comment commentOne = new Comment("Comment1", "USER1" , 10);		
		final Comment commentTwo = new Comment("Comment2", "USER2" , 20);
		
		commentsList.add(commentOne);
		commentsList.add(commentTwo);
		
		Comments comments = new Comments(commentsList, "1S");
		commentRepository.save(comments);

		commentRepository.deleteById("1S");
		Assertions.assertEquals(Optional.empty(), commentRepository.findById("1S"),
				"Successfully deleted a single a comment");
		
		LOGGER.info("\n\n **********End deleteComments Test********** \n\n");
	}

}
