package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.models.Comment;
import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.repositories.CommentRepository;
import de.thm.swtp.information_portal.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/allComments")
	public CompletableFuture<List<Comments>> findAllComments() throws InterruptedException {
		return CompletableFuture.completedFuture(commentService.findAllComments());
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/commentsByAnswerId/{id}")
	public CompletableFuture<ResponseEntity<Comments>> findByAnswerId(@PathVariable String id)  throws InterruptedException {
		Optional<Comments> comments = commentService.findByAnswerId(id);
		ResponseEntity<Comments> resComments = comments.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return CompletableFuture.completedFuture(resComments);
	}

	public CompletableFuture<ResponseEntity<Comments>> increaseCommentRating(@Valid @RequestBody Comments commentList){
		Optional<Comments> commentsToBeModified = commentService.findByAnswerId(commentList.getId());
		Comment modifiedComment = commentList.getComments().get(0);
		List<Comment> listOfComments = commentsToBeModified.get().getComments();
		listOfComments.forEach((item -> {
			if(item.equals(modifiedComment.getId())){
				int index = listOfComments.indexOf(item);
				listOfComments.set(index,modifiedComment);
			}
		}));
		commentsToBeModified.get().setComments(listOfComments);
		ResponseEntity<Comments> comRes = commentsToBeModified.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return CompletableFuture.completedFuture(comRes);
	}


	/**
	 * 
	 * @param commentList
	 * @return
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Async
	@PostMapping("/newComments")
	public CompletableFuture<ResponseEntity<Comments>> postComments(@RequestBody Comments commentList)
			throws URISyntaxException, InterruptedException {
		Optional<Comments> comments = commentService.findByAnswerId(commentList.getId());
		if (!comments.isPresent()) {
			List<Comment> newCommentList = new ArrayList<Comment>();
			newCommentList.add(commentList.getComments().get(0));
			Comments newComments = new Comments(newCommentList, commentList.getId());
			commentService.postComments(newComments);
			return CompletableFuture.completedFuture(
					ResponseEntity.created(new URI("/api/answer" + newComments.getId())).body(newComments));
		}

		else {
			List<Comment> commentsPresent = comments.get().getComments();
			commentsPresent.add(commentList.getComments().get(0));
			commentService.postComments(comments.get());
			return CompletableFuture.completedFuture(
					ResponseEntity.created(new URI("/api/answer" + comments.get().getId())).body(comments.get()));
		}
	}
}
