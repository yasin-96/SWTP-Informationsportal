package de.thm.swtp.information_portal.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;

	public Tag checkIfTagExists(String tagCheck) {
		Tag tag =  tagRepository.findByName(tagCheck);
		if(tag == null) {
			System.out.println("The tag " + tagCheck + " is getting created.");
			tag = tagRepository.save(new Tag(tagCheck));
		}
		System.out.println("This tag already exists");
		return tag;
	}
}
