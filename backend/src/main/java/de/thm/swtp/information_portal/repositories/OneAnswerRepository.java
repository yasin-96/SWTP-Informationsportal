package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Answer.Answer;

public interface OneAnswerRepository extends MongoRepository<Answer, String> {

}
