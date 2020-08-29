package de.thm.swtp.information_portal.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.TagRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private TagService tagService;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	UserRepository userRepository;

	/**
	 *
	 * @param tags
	 * @return
	 */
	public List<Question> findByTag(String tags) {

		var filteredQuestions = new HashSet<Question>();


		Tag existingTag = tagRepository.findByName(tags);
		List<Question> allQuestions = questionRepository.findAll();
		List<Question> questionByTags = new ArrayList<Question>();
		for (Question question : allQuestions) {
			for (Tag tag : question.getTags()) {
				if (existingTag.getName().toLowerCase().equals(tag.getName().toLowerCase())) {
					questionByTags.add(question);
				}
			}
		}
		return questionByTags;
	}

	/**
	 *
	 * @return
	 */
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	/**
	 *
	 * @param question
	 * @return
	 */
	public ResponseEntity<Question> editQuestion(Question question) throws URISyntaxException {
		List<Tag> tagList = tagService.checkIfTagsExist(question.getTags());
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
		List<Tag> newQuestionTags = tagService.checkIfTagsExist(question.getTags());
		Question newQuestion = new Question(question.getHeader(), question.getContent(), newQuestionTags,
				question.getUserId(),question.getUserName());
		questionRepository.save(newQuestion);
		return ResponseEntity.created(new URI("/api/question" +
				question.getId())).body(question);
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

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
