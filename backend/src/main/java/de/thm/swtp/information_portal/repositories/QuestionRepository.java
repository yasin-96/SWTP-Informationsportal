package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {

}
