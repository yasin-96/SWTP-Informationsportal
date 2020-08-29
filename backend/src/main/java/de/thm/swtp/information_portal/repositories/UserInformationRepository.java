package de.thm.swtp.information_portal.repositories;

import de.thm.swtp.information_portal.models.UserInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;


public interface UserInformationRepository extends MongoRepository<UserInformation, UUID> {
}
