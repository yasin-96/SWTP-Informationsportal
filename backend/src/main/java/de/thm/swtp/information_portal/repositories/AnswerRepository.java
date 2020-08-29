package de.thm.swtp.information_portal.repositories;

import de.thm.swtp.information_portal.models.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Answers;

import java.util.UUID;

public interface AnswerRepository extends MongoRepository<Answers, String> {
}
