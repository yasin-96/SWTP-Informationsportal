package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import de.thm.swtp.information_portal.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.service.AnswerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AnswerController {

	@Autowired
	private AnswerService answerService;

	/**
	 * 
	 * @param answerList
	 * @return
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Async
	@PostMapping("/answer")
	public CompletableFuture<ResponseEntity<Answers>> postAnswer(@RequestBody Answers answerList)
			throws URISyntaxException, InterruptedException {
		Optional<Answers> answers = answerService.findByQuestionId(answerList.getId());
		if (answers.isEmpty()) {
			List<Answer> newAnswerList = new ArrayList<Answer>();
			Answer newAnswer = new Answer(answerList.getListOfAnswers().get(0).getContent(), 0);
			newAnswerList.add(newAnswer);
			Answers newAnswers = new Answers(newAnswerList, answerList.getId());
			answerService.postAnswer(newAnswers);
			return CompletableFuture.completedFuture(
					ResponseEntity.created(new URI("/api/answer" + newAnswers.getId())).body(newAnswers));
		} else {
			List<Answer> answersPresent = answers.get().getListOfAnswers();
			Answer newAnswer = new Answer(answerList.getListOfAnswers().get(0).getContent(), 0);
			answersPresent.add(newAnswer);
			answerService.postAnswer(answers.get());
			return CompletableFuture.completedFuture(
					ResponseEntity.created(new URI("/api/answer" + answers.get().getId())).body(answers.get()));
		}
	}

	@Async
	@GetMapping("/answer/answerTobeEdited")
	public CompletableFuture<ResponseEntity<Answer>> getAnswerToBeEdited(@Valid @RequestBody String[] ids) {
		Optional<Answers> answers = answerService.findByQuestionId(ids[0]);
		List<Answer> answerList = answers.get().getListOfAnswers();
		for(var answer : answerList){
			if(answer.getId().equals(ids[1])){
				return CompletableFuture.completedFuture(new ResponseEntity<Answer>(answer,HttpStatus.OK));
			}
		}
		return  null;
	}


	@Async
	@PutMapping("/answer/{id}")
	public CompletableFuture<ResponseEntity<Answers>> editAnswer(@Valid @RequestBody Answers answersBody) throws URISyntaxException{
		Optional<Answers> answersToBeModified = answerService.findByQuestionId(answersBody.getId());
		Answer modifiedAnswer = answersBody.getListOfAnswers().get(0);
		List<Answer> listOfAnswers = answersToBeModified.get().getListOfAnswers();
		listOfAnswers.forEach((item-> {
			if(item.getId().equals(modifiedAnswer.getId())){
				int index = listOfAnswers.indexOf(item);
				listOfAnswers.set(index,modifiedAnswer);
			}
		}));
		answersToBeModified.get().setListOfAnswers(listOfAnswers);
		answerService.postAnswer(answersToBeModified.get());
		return CompletableFuture.completedFuture(ResponseEntity.created(new URI("/api/answer/" + answersToBeModified.get().getId())).body(answersToBeModified.get()));
	}


	/**
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/answersByQuestionId/{id}")
	public CompletableFuture<ResponseEntity<Answers>> getAnswers(@PathVariable String id) throws InterruptedException, InterruptedException {
		Optional<Answers> answers = answerService.findByQuestionId(id);
		ResponseEntity<Answers> answRes = answers.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return CompletableFuture.completedFuture(answRes);
	}

	@Async
	@PostMapping("/answer/increaseRating")
	public CompletableFuture<ResponseEntity<Answers>> increaseAnswerRating(@RequestBody Answers answerList){
		Optional<Answers> answersToBeModified = answerService.findByQuestionId(answerList.getId());
		Answer modifiedAnswer = answerList.getListOfAnswers().get(0);
		List<Answer> listOfAnswers = answersToBeModified.get().getListOfAnswers();
		listOfAnswers.forEach((item -> {
			if(item.getId().equals(modifiedAnswer.getId())){
				int index = listOfAnswers.indexOf(item);
				listOfAnswers.set(index,modifiedAnswer);
			}
		}));
		answersToBeModified.get().setListOfAnswers(listOfAnswers);
		ResponseEntity<Answers> answRes = answersToBeModified.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		answerService.postAnswer(answersToBeModified.get());
		return CompletableFuture.completedFuture(answRes);
	}
}
