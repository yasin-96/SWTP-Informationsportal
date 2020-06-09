package de.thm.swtp.information_portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mongodb.connection.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.service.QuestionService;
import de.thm.swtp.information_portal.service.TagService;

@RestController
@RequestMapping("/api")
public class QuestionController {
	
	@Autowired
	private QuestionService questionSerice;

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/allQuestions")
	public CompletableFuture<List<Question>> getAllQuestions() throws InterruptedException {
		var response = questionSerice.getAllQuestions();
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
		Optional<Question> question = questionSerice.getQuestion(id);
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
		List<Question> questions = questionSerice.findByTag(tag);
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
	public CompletableFuture<ResponseEntity<Question>> postQuestion(@Valid @RequestBody Question questionBody) throws URISyntaxException, InterruptedException{
		Question question = questionSerice.postQuestion(questionBody);
		return CompletableFuture.completedFuture( ResponseEntity.created(new URI("/api/questionById" + question.getId())).body(question));
	}


	//TODO: hier muss noch einiges gemacht werden
	@Async
	@PostMapping("/question/query")
	public CompletableFuture<ResponseEntity<ArrayList<Question>>> getDataByQuery(@Valid @RequestBody String searchQuery) throws URISyntaxException, InterruptedException{
		
		//split the string in parts and remove empty string
		var queryForTags = Arrays.asList(searchQuery.split(" "))
			.stream()
			.filter((item) -> !item.isEmpty())
			.collect(Collectors.toList());
		
		//result
		var allQuestions = new ArrayList<List<Question>>();

		//take each query and check if any question has this tag
		for(var query: queryForTags ) {
			try{
				var response = questionSerice.findByTag(query);
				if(response != null){
					allQuestions.add(response);
				}
			} catch(Exception e){
				System.out.println(e);
			}
		}

		System.out.println(allQuestions.toString());

		return null;
	}




}