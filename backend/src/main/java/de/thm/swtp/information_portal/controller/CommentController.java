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

import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.service.CommentService;

@RequestMapping("/api")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/allComments")
	public List<Comments> findAllComments(){
		return commentService.findAllComments();
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/commentsByAnswerId/{id}")
	public ResponseEntity<Comments> findByAnswerId(@PathVariable String id){
		Optional<Comments> comments = commentService.findByAnswerId(id);
		ResponseEntity<Comments> resComments = comments.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return resComments;
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/newComments")
	public ResponseEntity<Comments> postComments(@RequestBody Comments commentsBody) throws URISyntaxException{
		Comments comments = commentService.postComments(commentsBody);
		return ResponseEntity.created(new URI("/api/commentsByAnswerId" + comments.getId())).body(comments);
	}
}
