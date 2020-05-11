package de.thm.swtp.information_portal.service;

import java.util.List;
import java.util.Optional;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.repositories.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public Answer postAnswer(Answer answer) {
		return answerRepository.save(answer);
	}
	
	/*
	public List<Optional<Answer>> findByQuestion(String question){
		return answerRepository.findByQuestion(question);
		
	}*/
}
