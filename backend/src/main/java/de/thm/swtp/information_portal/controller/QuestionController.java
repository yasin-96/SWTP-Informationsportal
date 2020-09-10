package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
	 */
	@Async
	@GetMapping("/question/id/{id}")
	public CompletableFuture<ResponseEntity<Optional<Question>>> getQuestion(@PathVariable UUID id) {
		return CompletableFuture.completedFuture(questionService.getQuestion(id.toString()));
	}

	/**
	 *
	 * @return
	 */
	@Async
	@GetMapping("/question/all")
	public CompletableFuture<ResponseEntity<List<Question>>> getAllQuestions() {
		return CompletableFuture.completedFuture(questionService.getAllQuestions());
	}

	/**
	 * 
	 * @param tag
	 * @return CompletableFuture<ResponseEntity<List<Question>>>
	 */
	@Async
	@GetMapping("/question/tag/{tag}")
	public CompletableFuture<ResponseEntity<HashSet<Question>>> findByTag(@PathVariable String tag) {
		if(tag != null){
			return CompletableFuture.completedFuture(questionService.findAllTags(tag));
		}

		return CompletableFuture.completedFuture(
				ResponseEntity.status(HttpStatus.BAD_REQUEST)
						      .body(null)
		);
	}

	/**
	 * 
	 * @param questionBody
	 * @return CompletableFuture<ResponseEntity<Question>>
	 */
	@Async
	@PostMapping("/question/new")
	public CompletableFuture<ResponseEntity<Question>> postQuestion(
			@Validated @RequestBody Question questionBody,
			@AuthenticationPrincipal Jwt jwt) {

		if(jwt == null) {
			return CompletableFuture.completedFuture(
					ResponseEntity
							.status(HttpStatus.UNAUTHORIZED)
							.body(null)
			);
		}

		var userId = jwt.getClaimAsString("sub");
		var userName = jwt.getClaimAsString("preferred_username");


		return CompletableFuture
				.completedFuture(questionService.postQuestion(questionBody, userId, userName));
	}

	/**
	 *
	 * @param questionBody  the question which will be modified
	 * @return returns the modified question
	 */
	@Async
	@PutMapping("/question/update")
	public CompletableFuture<ResponseEntity<Question>> editQuestion(@Validated @RequestBody Question questionBody) {

		return CompletableFuture
				.completedFuture(questionService.editQuestion(questionBody));
	}

	/**
	 *
	 * @param query       Our entered search query from our frontend
	 * @return 					returns all questions for the search
	 */
	@Async
	@GetMapping("/question/query")
	public CompletableFuture<ResponseEntity<HashSet<Question>>> getDataByQuery(@Validated @RequestParam String query) {

		if(query != null){
			return CompletableFuture.completedFuture(questionService.findByManyTagNames(query));
		}

		return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
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
