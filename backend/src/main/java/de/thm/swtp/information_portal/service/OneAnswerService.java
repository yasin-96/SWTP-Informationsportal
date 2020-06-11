package de.thm.swtp.information_portal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.repositories.OneAnswerRepository;

@Service
public class OneAnswerService {
	
	@Autowired
	private OneAnswerRepository oneAnswerRepository;
	
	public Optional<Answer> getAnswer(String id) {
		return oneAnswerRepository.findById(id);
	}

	public Answer postAnswer(Answer newAnswer){
		return oneAnswerRepository.save(newAnswer);
	}
}
