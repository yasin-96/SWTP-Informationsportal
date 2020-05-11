package de.thm.swtp.information_portal.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.repositories.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;

	
	public List<Optional<Question>> findByTags(String tags){
		return questionRepository.findByTags(tags);
	}
	

	public List<Question> getAllQuestions(){
		return questionRepository.findAll();
	}
	
	public Question postQuestion(Question question) {
		return questionRepository.save(question);
	}
	
	public Optional<Question> getQuestion(String id) {
		Optional<Question> question = questionRepository.findById(id);
		return question;
	}
}
