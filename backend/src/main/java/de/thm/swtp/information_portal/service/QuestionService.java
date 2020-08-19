package de.thm.swtp.information_portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 *
	 * @param tags
	 * @return
	 */
	public List<Question> findByTag(String tags) {

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
	public Question editQuestion(Question question){
		return questionRepository.save(question);
	}

	/**
	 *
	 * @param question
	 * @return
	 */
	public Question postQuestion(Question question) {
		List<Tag> newQuestionTags = tagService.checkIfTagsExist(question.getTags());
		Question newQuestion = new Question(question.getHeader(), question.getContent(), newQuestionTags, question.getUserId());
		return questionRepository.save(newQuestion);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public Optional<Question> getQuestion(String id) {
		return questionRepository.findById(id);
	}
}
