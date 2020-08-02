package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
// import javax.validation.Valid;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.service.AnswerService;
import de.thm.swtp.information_portal.service.TagService;
import de.thm.swtp.information_portal.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.service.QuestionService;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class QuestionController {

	@Autowired
	private TagService tagService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/allQuestions")
	public CompletableFuture<List<Question>> getAllQuestions() throws InterruptedException {
		var response = questionService.getAllQuestions();

		setParsedUserNameById(response);

		return CompletableFuture.completedFuture(response);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/questionById/{id}")
	public CompletableFuture<ResponseEntity<Question>> getQuestion(@PathVariable String id)
			throws InterruptedException {
		Optional<Question> question = questionService.getQuestion(id);

		if(question.get().getUserId() != null) {
			var userName = userService.getUser(question.get().getUserId()).get().getPreferred_username();
			question.get().setUserName( !userName.isEmpty() || userName != null ? userName : "Unknown");
		} else {
			question.get().setUserName("Unknown");
		}


		ResponseEntity<Question> quest = question.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		return CompletableFuture.completedFuture(quest);
	}

	/**
	 * 
	 * @param tag
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/questionByTag/{tag}")
	public CompletableFuture<ResponseEntity<List<Question>>> findByTag(@PathVariable String tag)
			throws InterruptedException {
		List<Question> questions = questionService.findByTag(tag);
		
		setParsedUserNameById(questions);

		return CompletableFuture.completedFuture(new ResponseEntity<List<Question>>(questions, HttpStatus.OK));
	}

	/**
	 * 
	 * @param questionBody
	 * @return
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Async
	@PostMapping("/newQuestion")
	public CompletableFuture<ResponseEntity<Question>> postQuestion(@Validated @RequestBody Question questionBody,
			@AuthenticationPrincipal Jwt jwt)
			throws URISyntaxException, InterruptedException {

		Question quest = new Question(questionBody.getHeader(), questionBody.getContent(), questionBody.getTags(),
				jwt.getClaimAsString("sub"));

		Question question = questionService.postQuestion(quest);
		return CompletableFuture
				.completedFuture(ResponseEntity.created(new URI("/api/question" + question.getId())).body(question));
	}

	/**
	 *
	 * @param questionBody
	 * @return
	 * @throws URISyntaxException
	 */
	@Async
	@PutMapping("/question")
	public CompletableFuture<ResponseEntity<Question>> editQuestion(@Validated @RequestBody Question questionBody)
			throws URISyntaxException {
		List<Tag> tagList = tagService.checkIfTagsExist(questionBody.getTags());
		questionBody.setTags(tagList);
		Question question = questionService.editQuestion(questionBody);
		return CompletableFuture
				.completedFuture(ResponseEntity.created((new URI("/api/question" + question.getId()))).body(question));
	}

	/**
	 *
	 * @param searchQuery
	 * @return
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/question/query")
	public CompletableFuture<ResponseEntity<List<Question>>> getDataByQuery(@Validated @RequestParam String searchQuery)
			throws URISyntaxException, InterruptedException {



		List<String> listQuery = Arrays.stream(searchQuery.toUpperCase().split(" ")).filter(item -> !item.isEmpty())
				.collect(Collectors.toList());

		var filteredQuestions = new HashSet<Question>();

		for (var query : listQuery) {
			try {
				var response = questionService.findByTag(query);

				if (response != null) {
					filteredQuestions.addAll(response);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		var filteredQuestionsAsList = new ArrayList<Question>(filteredQuestions);

		setParsedUserNameById(filteredQuestionsAsList);
		
		return CompletableFuture.completedFuture(new ResponseEntity<>(filteredQuestionsAsList, HttpStatus.OK));
	}

	/**
	 *
	 * @return
	 */
	@Async
	@GetMapping("/question/getMostActiveQuestions")
	public CompletableFuture<ResponseEntity<List<Question>>> getMostActiveQuestions() {
		List<Question> allQuestions = questionService.getAllQuestions();
		List<Question> mostActiveQuestions = new ArrayList<>();
		Map<Question, Integer> myMap = new HashMap<>();
		for (var item : allQuestions) {
			Optional<Answers> answers = answerService.findByQuestionId(item.getId());
			answers.ifPresent(value -> myMap.put(item, value.getListOfAnswers().size()));
		}
		mostActiveQuestions = getListOfMostActiveQuestions(myMap);
		setParsedUserNameById(mostActiveQuestions);
		return CompletableFuture.completedFuture(new ResponseEntity<>(mostActiveQuestions, HttpStatus.OK));
	}

	/**
	 *
	 * @param myMap
	 * @return
	 */
	public List<Question> getListOfMostActiveQuestions(Map<Question, Integer> myMap) {
		List<Question> questions = new ArrayList<>();
		Map<Question, Integer> sorted = myMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		for (var entry : sorted.entrySet()) {
			questions.add(entry.getKey());
		}
		setParsedUserNameById(questions);

		return questions.stream().limit(12).collect(Collectors.toList());
	}

	/**
	 *
	 * @param question
	 */
	public void setParsedUserNameById(Optional<Question> question){
		if(question.get().getUserId() != null) {
			var userName = userService.getUser(question.get().getUserId()).get().getPreferred_username();
			question.get().setUserName( !userName.isEmpty() || userName != null ? userName : "Unknown");
		} else {
			question.get().setUserName("Unknown");
		}
	}

	/**
	 *
	 * @param listOfQuestions
	 */
	public void setParsedUserNameById(List<Question> listOfQuestions){
		
		listOfQuestions.forEach(question -> {
			if(question.getUserId() != null) {
				var userName = userService.getUser(question.getUserId()).get().getPreferred_username();
				question.setUserName( !userName.isEmpty() || userName != null ? userName : "Unknown");
			} else {
				question.setUserName("Unknown");
			}
		});
	}
}
