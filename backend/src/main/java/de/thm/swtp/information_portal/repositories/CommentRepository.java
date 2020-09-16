package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Comment.Comments;

public interface CommentRepository extends MongoRepository<Comments, String> {

}