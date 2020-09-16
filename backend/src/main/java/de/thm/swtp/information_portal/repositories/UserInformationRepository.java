package de.thm.swtp.information_portal.repositories;

import de.thm.swtp.information_portal.models.User.UserInformation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserInformationRepository extends MongoRepository<UserInformation, String> {
}
