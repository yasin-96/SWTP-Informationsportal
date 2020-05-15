package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.thm.swtp.information_portal.models.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {
	
	Tag findByName(String tag);

}
