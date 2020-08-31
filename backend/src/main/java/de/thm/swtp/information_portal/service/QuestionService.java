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


	public ResponseEntity<HashSet<Question>> findByTagName(String searchQuery){
		var listQuery = Arrays.stream(searchQuery.toUpperCase().split(" "))
				.filter(item -> !item.isEmpty())
				.collect(Collectors.toList());

		var filteredQuestions = new HashSet<Question>();

		for (var query : listQuery) {
			var foundedTag = this.findTag(query);
			if (foundedTag != null) {
				filteredQuestions.addAll(foundedTag);
			}
		}

		if(filteredQuestions != null) {
			return new ResponseEntity(filteredQuestions, HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	public List<Question> findTag(String tagToFind) {
		var questionByTags = new ArrayList<Question>();
		var existingTag = tagRepository.findByName(tagToFind);

		if(existingTag != null){
			var allQuestions = questionRepository.findAll();

			if(allQuestions != null){
				questionByTags = new ArrayList<Question>();
				for (var question : allQuestions) {
					for (Tag tag : question.getTags()) {
						if (existingTag.getName().toLowerCase().equals(tag.getName().toLowerCase())) {
							questionByTags.add(question);
						}
					}
				}
			}
		}

		return questionByTags != null ? questionByTags : null;
	}

	public ResponseEntity<List<Question>> findAllTags(String tagToFind){
		var response = this.findTag(tagToFind);

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
	public ResponseEntity<Question> postQuestion(Question question, String userId, String userName) throws URISyntaxException {
		var newQuestionTags = tagService.checkIfTagsExist(question.getTags());

		var newQuestion = new Question(
				question.getHeader(),
				question.getContent(),
				newQuestionTags,
				userId,
				userName
		);

		//TODO prüfen??
		var response = questionRepository.save(newQuestion);

		return new ResponseEntity(response, HttpStatus.OK);
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
