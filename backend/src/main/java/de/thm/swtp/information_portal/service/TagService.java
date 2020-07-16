package de.thm.swtp.information_portal.service;



import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;

	public List<Tag> checkIfTagsExist(List<Tag> tags) {
		List<Tag> newTagList = new ArrayList<Tag>();

		for(Tag tag:tags) {
			Tag newTag = tagRepository.findByName(tag.getName());

			if(newTag == null) {
				int index = tags.indexOf(tag);
				System.out.println("The tag " + tags.get(index).getName() +
					" is getting created.");
				Tag tagToBeAdded = new Tag(tags.get(index).getName());
				tagRepository.save(tagToUpperCase(tagToBeAdded));
				newTagList.add(tagToUpperCase(tagToBeAdded));
			}
			else {
				newTagList.add(tagToUpperCase(newTag));
			}
	
		}
		return newTagList;
	}
	
	public List<Tag> getAllTags() {
		return tagRepository.findAll();
	}

	public Tag tagToUpperCase(Tag tag){
		//List<Tag> tagsInUpperCase = new ArrayList<>();
		tag.setName(tag.getName().toUpperCase());
		return tag;
	}
}
		
	


