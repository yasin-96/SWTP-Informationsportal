package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Answer;

import java.util.UUID;

public interface OneAnswerRepository extends MongoRepository<Answer, String> {

}
