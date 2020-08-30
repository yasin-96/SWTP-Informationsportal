package de.thm.swtp.information_portal.controller;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
// import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.service.QuestionService;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/**
	 *
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/question/id/{id}")
	public CompletableFuture<ResponseEntity<Question>> getQuestion(@PathVariable UUID id)
			throws InterruptedException {
		return CompletableFuture.completedFuture(questionService.getQuestion(id.toString()));
	}

	/**
	 *
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/question/all")
	public CompletableFuture<ResponseEntity<List<Question>>> getAllQuestions() throws InterruptedException {
		return CompletableFuture.completedFuture(questionService.getAllQuestions());
	}

	/**
	 * 
	 * @param tag
	 * @return CompletableFuture<ResponseEntity<List<Question>>>
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/question/tag/{tag}")
	public CompletableFuture<ResponseEntity<List<Question>>> findByTag(@PathVariable String tag)
			throws InterruptedException {
		if(tag != null){
			return CompletableFuture.completedFuture(questionService.findByTag(tag));
		}

		return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.BAD_REQUEST));

	}

	/**
	 * 
	 * @param questionBody
	 * @return CompletableFuture<ResponseEntity<Question>>
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Async
	@PostMapping("/question/new")
	public CompletableFuture<ResponseEntity<Question>> postQuestion(@Validated @RequestBody Question questionBody,
			@AuthenticationPrincipal Jwt jwt)
			throws URISyntaxException {

		Question quest = new Question(
				questionBody.getHeader(),
				questionBody.getContent(),
				questionBody.getTags(),
				jwt.getClaimAsString("sub"),
				jwt.getClaimAsString("preferred_username")
		);


		return CompletableFuture
				.completedFuture(questionService.postQuestion(quest));
	}

	/**
	 *
	 * @param questionBody  the question which will be modified
	 * @return returns the modified question
	 * @throws URISyntaxException
	 */
	@Async
	@PutMapping("/question/update")
	public CompletableFuture<ResponseEntity<Question>> editQuestion(@Validated @RequestBody Question questionBody)
			throws URISyntaxException {

		return CompletableFuture
				.completedFuture(questionService.editQuestion(questionBody));
	}

	/**
	 *
	 * @param searchQuery       Our entered search query from our frontend
	 * @return 					returns all questions for the search
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/question/query")
	public CompletableFuture<ResponseEntity<List<Question>>> getDataByQuery(@Validated @RequestParam String searchQuery)
			throws URISyntaxException, InterruptedException {

		if(searchQuery != null){
			return CompletableFuture.completedFuture(questionService.findByTag(searchQuery));
		}

		return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.BAD_REQUEST));
	}

	/**
	 *
	 * @return returns the most active questions as a list
	 */
	@Async
	@GetMapping("/question/active")
	public CompletableFuture<ResponseEntity<List<Question>>> getMostActiveQuestions() {
		return CompletableFuture.completedFuture(questionService.mostActiveQuestions());
	}


}
