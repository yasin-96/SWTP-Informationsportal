package de.thm.swtp.information_portal.service;



import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;


import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;

import static java.util.stream.Collectors.toMap;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private QuestionRepository questionRepository;
	/**
	 *
	 * @param tags
	 * @return
	 */
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

	/**
	 *
	 * @return
	 */
	public List<Tag> getAllTags() {
		return tagRepository.findAll();
	}

	/**
	 *
	 * @param tag
	 * @return
	 */
	public Tag tagToUpperCase(Tag tag){
		//List<Tag> tagsInUpperCase = new ArrayList<>();
		tag.setName(tag.getName().toUpperCase());
		return tag;
	}

	public ResponseEntity<List<Tag>>  getTagsWithMostQuestions() {
		List<Question> allQuestions = questionRepository.findAll();
		List<Tag> allTags = this.getAllTags();
		Map<Tag, Long> map = allQuestions.stream().flatMap(q -> q.getTags().stream()).filter(allTags::contains)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		var mostActiveTags = getTagsWithMostQuestions(map);
		//TODO was it wenn mostactivetags leer ist??
		return new ResponseEntity<>(mostActiveTags, HttpStatus.OK);
	}

	/**
	 *
	 * @param myMap
	 * @return
	 */
	public List<Tag> getTagsWithMostQuestions(Map<Tag, Long> myMap) {
		List<Tag> tags = new ArrayList<>();
		Map<Tag, Long> sorted = myMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		for (var entry : sorted.entrySet()) {
			tags.add(entry.getKey());
		}

		return tags.stream().limit(12).collect(Collectors.toList());
	}
}
		
	


