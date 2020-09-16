package de.thm.swtp.information_portal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import de.thm.swtp.information_portal.models.Tag.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {
	
	//List<Tag> findByName(List<Tag> tags);
	
	Tag findByName(String tag);
}
