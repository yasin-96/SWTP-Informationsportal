package de.thm.swtp.information_portal.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.service.OneAnswerService;

@RestController
@RequestMapping("/api")
public class OneAnswerController {
	
	@Autowired
	private OneAnswerService OneAnswerService;
	
	@Async
	@GetMapping("/getOneAnswer/{id}")
	public CompletableFuture<ResponseEntity<Answer>> getOneAnswer(@PathVariable String id) throws InterruptedException{
		Optional<Answer> answer = OneAnswerService.getAnswer(id);
		ResponseEntity<Answer> resAnswer = answer.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return CompletableFuture.completedFuture( resAnswer);
	}
}
