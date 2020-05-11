package de.thm.swtp.information_portal.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Answers;

public interface AnswerRepository extends MongoRepository<Answers, String> {	
}
