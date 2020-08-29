package de.thm.swtp.information_portal.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
}