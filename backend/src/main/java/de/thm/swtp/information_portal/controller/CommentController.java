package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@CrossOrigin(origins="*")
@RequestMapping("/api")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	
	
	@GetMapping("/allComments")
	public List<Comments> findAllComments(){
		return commentService.findAllComments();
	}

	
	@GetMapping("/commentsByAnswerId/{id}")
	public ResponseEntity<Comments> findByAnswerId(@PathVariable String id){
		Optional<Comments> comments = commentService.findByAnswerId(id);
		ResponseEntity<Comments> resComments = comments.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return resComments;
	}
	
	
	@PostMapping("/newComments")
	public ResponseEntity<Comments> postComments(@RequestBody Comments commentList) throws URISyntaxException{
		Optional<Comments> comments = commentService.findByAnswerId(commentList.getId());
		List<Comment> commentsPresent = comments.get().getComments();
		commentsPresent.add(commentList.getComments().get(0));
		commentService.postComments(comments.get());
		return ResponseEntity.created(new URI("/api/answer" + comments.get().getId())).body(comments.get());
	}
}
