package de.thm.swtp.information_portal.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.TagRepository;

import static java.util.stream.Collectors.toMap;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private TagService tagService;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private AnswerRepository answerRepository;

	/**
	 *
	 * @param searchQuery
	 * @return
	 */
	public ResponseEntity<List<Question>> findByTag(String searchQuery) {

		var response = Arrays.stream(searchQuery.toUpperCase().split(" "))
				.filter(item -> !item.isEmpty())
				.collect(Collectors.toList())
				.stream()
				.map(query -> {
					var existingTag = tagRepository.findByName(query);

					if(existingTag != null){
						var allQuestions = questionRepository.findAll();

						if(allQuestions != null){
							var questionByTags = allQuestions.stream()
									.map(listOftag -> listOftag.getTags())
									.map(tagsList -> tagsList.stream()
											.filter(item ->
												item.getName().toLowerCase().equals(existingTag.getName().toLowerCase())
											)
									);
							return questionByTags;
						}
					}
					return null;
				})
				.filter(list -> list != null);

		if(response != null){
			return new ResponseEntity(response, HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	/**
	 *
	 * @return
	 */
	public ResponseEntity<List<Question>> getAllQuestions() {
		var allQuestions = questionRepository.findAll();

		if(allQuestions != null){
			return new ResponseEntity(allQuestions, HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	/**
	 *
	 * @param question
	 * @return
	 */
	public ResponseEntity<Question> editQuestion(Question question) throws URISyntaxException {
		var tagList = tagService.checkIfTagsExist(question.getTags());
		question.setTags(tagList);
		questionRepository.save(question);
		return ResponseEntity.created((new URI("/api/question" + question.getId()))).body(question);
	}

	/**
	 *
	 * @param question
	 * @return
	 */
	public ResponseEntity<Question> postQuestion(Question question) throws URISyntaxException {
		var newQuestionTags = tagService.checkIfTagsExist(question.getTags());
		var newQuestion = new Question(
				question.getHeader(),
				question.getContent(),
				newQuestionTags,
				question.getUserId(),
				question.getUserName()
		);
		questionRepository.save(newQuestion);

		return new ResponseEntity(question, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<Question> getQuestion(String id) {
		var question = questionRepository.findById(id);

		if(question.isPresent()){
			return new ResponseEntity(question, HttpStatus.OK);
		}

		return new ResponseEntity(question, HttpStatus.OK);
	}



	public ResponseEntity<List<Question>> mostActiveQuestions(){
		var allQuestions = questionRepository.findAll();
		Map<Question, Integer> myMap = new HashMap<>();

		for (var item : allQuestions) {
			Optional<Answers> answers = answerRepository.findById(item.getId());
			answers.ifPresent(value -> myMap.put(item, value.getListOfAnswers().size()));
		}

		return new ResponseEntity(
				getListOfMostActiveQuestions(myMap),
				HttpStatus.OK
		);
	}

	/**
	 *
	 * @param  myMap   		map as parameter for our mostActiveQuestions() method
	 * @return Returns 		the sorted list for our mostActiveQuestions() method
	 */
	public List<Question> getListOfMostActiveQuestions(Map<Question, Integer> myMap) {
		List<Question> questions = new ArrayList<>();
		Map<Question, Integer> sorted = myMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		for (var entry : sorted.entrySet()) {
			questions.add(entry.getKey());
		}

		return questions.stream()
				.limit(20)
				.collect(Collectors.toList());
	}
}
