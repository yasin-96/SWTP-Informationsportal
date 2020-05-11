package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.service.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionController {
	
	@Autowired
	private QuestionService questionSerice;
	
	@GetMapping("/allQuestions")
	public List<Question> getAllQuestions() {
		return questionSerice.getAllQuestions();
	}
	
	
	@GetMapping("/questionById")
	public ResponseEntity<Question> getQuestion(@RequestBody String id){
		Optional<Question> question = questionSerice.getQuestion(id);
		ResponseEntity<Question> quest = question.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return quest;
	}
	
	@GetMapping("/questionTags")
	public List<Optional<Question>> findByTag(@RequestBody String tag){
		return questionSerice.findByTags(tag);
	}
	
	@PostMapping("/newQuestion")
	public ResponseEntity<Question> postQuestion(@Valid @RequestBody Question questionBody) throws URISyntaxException{
		Question question = questionSerice.postQuestion(questionBody);
		return ResponseEntity.created(new URI("/api/question" + question.getId())).body(question);
	}
}