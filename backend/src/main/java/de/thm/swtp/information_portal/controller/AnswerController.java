package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.service.AnswerService;

@RestController
@RequestMapping("/api")
public class AnswerController {

	@Autowired
	private AnswerService answerService;
	
	@PostMapping("/answer")
	public ResponseEntity<Answers> postAnswer(@RequestBody Answers answerList) throws URISyntaxException{
		Answers answers = answerService.postAnswer(answerList);
		return ResponseEntity.created(new URI("/api/answer" + answers.getId())).body(answers);
	}
	
	@GetMapping("/answersByQuestionId")
	public ResponseEntity<Answers> getAnswers(@RequestBody String id ){
		Optional<Answers> answers = answerService.findByQuestionId(id);
		ResponseEntity<Answers> answRes = answers.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return answRes;
	}
	

}
