package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Answer.Answers;

public interface AnswerRepository extends MongoRepository<Answers, String> {
}
