package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.service.AnswerService;

@RestController
@RequestMapping("/api")
public class AnswerController {

	@Autowired
	private AnswerService answerService;
	
	@PostMapping("/answer")
	public ResponseEntity<Answer> postAnswer(@RequestBody Answer answerBody) throws URISyntaxException{
		Answer answer = answerService.postAnswer(answerBody);
		return ResponseEntity.created(new URI("/api/answer" + answer.getId())).body(answer);
	}
	
	@GetMapping("/question/{id}/answers")
	public List<Optional<Answer>> findByQuestion(@PathVariable String id){
		return answerService.findByQuestion(id);
	}
}
