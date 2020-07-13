package de.thm.swtp.information_portal.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.thm.swtp.information_portal.models.Answers;
import de.thm.swtp.information_portal.repositories.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public Answers postAnswer(Answers answerList) {
		return answerRepository.save(answerList);
	}
	
	public Optional<Answers> findByQuestionId(String id) {
		return answerRepository.findById(id);
	}
	

}
