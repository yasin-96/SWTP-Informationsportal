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
	public CompletableFuture<ResponseEntity<Comments>> findByAnswerId(@PathVariable String id)
			throws InterruptedException {
		Optional<Comments> comments = commentService.findByAnswerId(id);
		if (comments.isPresent()) {
			List<Comment> allComments = comments.get().getComments();

			allComments.forEach(comment -> {

				if(comment.getUserId() != null) {

					var userName = userService.getUser(comment.getUserId()).get().getPreferred_username();

					comment.setUserName(
						!userName.isEmpty() && userName != null ? userName : "Unknown");
				} else {
					comment.setUserName("Unknown");
				}
			});




			allComments.sort(compareByRating);
		}
		ResponseEntity<Comments> resComments = comments.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return CompletableFuture.completedFuture(resComments);
	}

	Comparator<Comment> compareByRating = new Comparator<Comment>() {
		@Override
		public int compare(Comment o1, Comment o2) {
			return o2.getRating() - o1.getRating();
		}
	};

	@Async
	@PostMapping("/comment/increaseRating")
	public CompletableFuture<ResponseEntity<Comments>> increaseCommentRating(
			@Validated @RequestBody Comments commentList) {
		Optional<Comments> commentsToBeModified = commentService.findByAnswerId(commentList.getId());
		Comment modifiedComment = commentList.getComments().get(0);
		List<Comment> listOfComments = commentsToBeModified.get().getComments();
		listOfComments.forEach((item -> {
			if (item.getId().equals(modifiedComment.getId())) {
				int index = listOfComments.indexOf(item);
				listOfComments.set(index, modifiedComment);
			}
		}));
		commentsToBeModified.get().setComments(listOfComments);
		ResponseEntity<Comments> comRes = commentsToBeModified.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		commentService.postComments(commentsToBeModified.get());
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
	public CompletableFuture<ResponseEntity<Comments>> postComments(@RequestBody Comments commentList, @AuthenticationPrincipal Jwt jwt)
			throws URISyntaxException, InterruptedException {
		Optional<Comments> comments = commentService.findByAnswerId(commentList.getId());
		Comment existingComment = commentList.getComments().get(0);
		if (!comments.isPresent()) {
			List<Comment> newCommentList = new ArrayList<Comment>();
			Comment newComment = new Comment(existingComment.getContent(), jwt.getClaimAsString("sub"),
					existingComment.getRating());
			newCommentList.add(newComment);
			Comments newComments = new Comments(newCommentList, commentList.getId());
			commentService.postComments(newComments);
			return CompletableFuture.completedFuture(
					ResponseEntity.created(new URI("/api/answer" + newComments.getId())).body(newComments));
		}

		else {
			List<Comment> commentsPresent = comments.get().getComments();
			Comment newComment = new Comment(existingComment.getContent(), jwt.getClaimAsString("sub"),
					existingComment.getRating());
			commentsPresent.add(newComment);
			comments.get().setComments(commentsPresent);
			commentService.postComments(comments.get());
			return CompletableFuture.completedFuture(
					ResponseEntity.created(new URI("/api/answer" + comments.get().getId())).body(comments.get()));
		}
	}
}
