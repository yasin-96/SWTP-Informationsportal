package de.thm.swtp.information_portal.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.User.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
}