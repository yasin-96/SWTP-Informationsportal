package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Comments;

import java.util.UUID;

public interface CommentRepository extends MongoRepository<Comments, String> {

}