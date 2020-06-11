package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.service.OneAnswerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class OneAnswerController {
	
	@Autowired
	private OneAnswerService oneAnswerService;
	
	@Async
	@GetMapping("/getOneAnswer/{id}")
	public CompletableFuture<ResponseEntity<Answer>> getOneAnswer(@PathVariable String id) throws InterruptedException{
		Optional<Answer> answer = oneAnswerService.getAnswer(id);
		ResponseEntity<Answer> resAnswer = answer.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return CompletableFuture.completedFuture( resAnswer);
	}

	@Async
	@PostMapping("/oneNewAnswer")
	public CompletableFuture<ResponseEntity<Answer>> postOneAnswer(@Valid @RequestBody Answer newAnswer) throws URISyntaxException {
		Answer answer = oneAnswerService.postAnswer(newAnswer);
		return CompletableFuture.completedFuture( ResponseEntity.created(new URI("/api/answer" + answer.getId())).body(answer));
	}

	@Async
	@PostMapping("/increaseRatingOfAnswer")
	public CompletableFuture<ResponseEntity<Answer>> increaseAnswerRating(@RequestBody String answerId) throws URISyntaxException {
		Optional<Answer> answer = oneAnswerService.getAnswer(answerId);
		Answer unwrappedAnswer = answer.get();
		unwrappedAnswer.setRating(unwrappedAnswer.getRating()+1);
		oneAnswerService.postAnswer(unwrappedAnswer);
		return CompletableFuture.completedFuture(ResponseEntity.created(new URI("/api/answer" + unwrappedAnswer.getId())).body(unwrappedAnswer));
	}
}
