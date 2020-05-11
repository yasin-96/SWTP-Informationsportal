package de.thm.swtp.information_portal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Answer;
import de.thm.swtp.information_portal.models.Question;

public interface AnswerRepository extends MongoRepository<Answer, String> {

	//public List<Optional<Answer>> findByQuestion(String question);
}
