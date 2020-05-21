package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.service.QuestionService;
import de.thm.swtp.information_portal.service.TagService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuestionController {
	
	@Autowired
	private QuestionService questionSerice;

	
	@GetMapping("/allQuestions")
	public List<Question> getAllQuestions() {
		return questionSerice.getAllQuestions();
	}
	
	
	@GetMapping("/questionById/{id}")
	public ResponseEntity<Question> getQuestion(@PathVariable String id){
		Optional<Question> question = questionSerice.getQuestion(id);
		ResponseEntity<Question> quest = question.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return quest;
	}
	
	
	@GetMapping("/questionByTag/{tag}")
	public ResponseEntity<List<Question>> findByTag(@PathVariable String tag){
		List<Question> questions = questionSerice.findByTag(tag);
		return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
	}
	
	@PostMapping("/newQuestion")
	public ResponseEntity<Question> postQuestion(@Valid @RequestBody Question questionBody) throws URISyntaxException{
		Question question = questionSerice.postQuestion(questionBody);
		return ResponseEntity.created(new URI("/api/questionById" + question.getId())).body(question);
	}
}