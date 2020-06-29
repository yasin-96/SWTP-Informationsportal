package de.thm.swtp.information_portal.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Collectors;

import javax.validation.Valid;



import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.service.AnswerService;
import de.thm.swtp.information_portal.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.service.QuestionService;
import java.util.Collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.*;



@RestController
@RequestMapping("/api")
public class QuestionController {

	@Autowired
	private TagService tagService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/allQuestions")
	public CompletableFuture<List<Question>> getAllQuestions() throws InterruptedException {
		var response = questionService.getAllQuestions();
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
	public CompletableFuture<ResponseEntity<Question>> getQuestion(@PathVariable String id) throws InterruptedException{
		Optional<Question> question = questionService.getQuestion(id);
		ResponseEntity<Question> quest = question.map(response->ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
	public CompletableFuture<ResponseEntity<List<Question>>> findByTag(@PathVariable String tag) throws InterruptedException {
		List<Question> questions = questionService.findByTag(tag);
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
	public CompletableFuture<ResponseEntity<Question>> postQuestion(@Valid @RequestBody Question questionBody, Jwt jwt) throws URISyntaxException, InterruptedException{

		Question quest = new Question(questionBody.getHeader(), questionBody.getContent(), questionBody.getTags(), jwt.getClaim("sub"));

		Question question = questionService.postQuestion(quest);
		return CompletableFuture.completedFuture( ResponseEntity.created(new URI("/api/question" + question.getId())).body(question));
	}

	@Async
	@PutMapping("/question")
	public CompletableFuture<ResponseEntity<Question>> editQuestion(@Valid @RequestBody Question questionBody) throws URISyntaxException{
		List<Tag> tagList = tagService.checkIfTagsExist(questionBody.getTags());
		questionBody.setTags(tagList);
		Question question = questionService.editQuestion(questionBody);
		return CompletableFuture.completedFuture(ResponseEntity.created((new URI("/api/question" + question.getId()))).body(question));
	}



	@Async
	@GetMapping("/question/query")
	public CompletableFuture<ResponseEntity<List<Question>>> getDataByQuery(@Valid @RequestParam String searchQuery) throws URISyntaxException, InterruptedException{

		List<String> listQuery = Arrays.stream(searchQuery.split(" "))
				.filter(item-> !item.isEmpty())
				.collect(Collectors.toList());

		var filteredQuestions = new ArrayList<Question>();

		for(var query: listQuery ) {
			try{
				var response = questionService.findByTag(query);

				if(response != null){
					filteredQuestions.addAll(response);
				}
			} catch(Exception e){
				System.out.println(e);
			}
		}
		return CompletableFuture.completedFuture(new ResponseEntity<>(filteredQuestions, HttpStatus.OK));
	}


	@Async
	@GetMapping("/question/getMostActiveQuestions")
	public CompletableFuture<ResponseEntity<List<Question>>> getMostActiveQuestions(){
		List<Question> allQuestions = questionService.getAllQuestions();
		List<Question> mostActiveQuestions = new ArrayList<>();
		Map<Question,Integer> myMap = new HashMap<>();
		for(var item: allQuestions) {
			Optional<Answers> answers = answerService.findByQuestionId(item.getId());
			answers.ifPresent(value -> myMap.put(item, value.getListOfAnswers().size()));
		}
			mostActiveQuestions = getListOfMostActiveQuestions(myMap);
		return CompletableFuture.completedFuture(new ResponseEntity<>(mostActiveQuestions, HttpStatus.OK));
	}

	public List<Question> getListOfMostActiveQuestions(Map<Question,Integer> myMap){
		List<Question> questions = new ArrayList<>();
		Map<Question, Integer> sorted = myMap
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(
						toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
								LinkedHashMap::new));

		for (var entry : sorted.entrySet()) {
			questions.add(entry.getKey());
		}
		return questions.stream().limit(12).collect(Collectors.toList());
	}
}
