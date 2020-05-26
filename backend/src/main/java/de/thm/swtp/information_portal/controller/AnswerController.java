package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import de.thm.swtp.information_portal.service.AnswerService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AnswerController {

	@Autowired
	private AnswerService answerService;
	
	@PostMapping("/answer")
	public ResponseEntity<Answers> postAnswer(@RequestBody Answers answerList) throws URISyntaxException{
			Optional<Answers> answers = answerService.findByQuestionId(answerList.getId());
			if(answers==null) {
				List<Answer> newAnswerList = new ArrayList<Answer>();
				newAnswerList.add(answerList.getListOfAnswers().get(0));
				Answers newAnswers = new Answers(newAnswerList,answerList.getId());
				answerService.postAnswer(newAnswers);
				return ResponseEntity.created(new URI("/api/answer" + newAnswers.getId())).body(newAnswers);
			}
			else {
			List<Answer> answersPresent = answers.get().getListOfAnswers();
			answersPresent.add(answerList.getListOfAnswers().get(0));
			answerService.postAnswer(answers.get());
			return ResponseEntity.created(new URI("/api/answer" + answers.get().getId())).body(answers.get());
			}
	}
	
	
	@GetMapping("/answersByQuestionId/{id}")
	public ResponseEntity<Answers> getAnswers(@PathVariable String id ){
		Optional<Answers> answers = answerService.findByQuestionId(id);
		ResponseEntity<Answers> answRes = answers.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return answRes;
	}
	

}
