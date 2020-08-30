package de.thm.swtp.information_portal.service;



import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;

import static java.util.stream.Collectors.collectingAndThen;
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

	public ResponseEntity<Tag> getTagByName(String tagName){
		var allFoundedTag = tagRepository.findByName(tagName);

		if(allFoundedTag != null){
			return new ResponseEntity(allFoundedTag, HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
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
		var allQuestions = questionRepository.findAll();

		if(allQuestions != null){
			var allTags = tagRepository.findAll();

			if(allTags != null){
				Map<Tag, Long> map = allQuestions.stream()
						.flatMap(quest -> quest.getTags().stream())
						.filter(allTags::contains)
						.collect(Collectors
								.groupingBy(Function.identity(), Collectors.counting())
						);
				var listOfTags = getTagsWithMostQuestions(map);

				return new ResponseEntity(
						listOfTags,
						HttpStatus.OK);
			}
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	/**
	 *
	 * @param myMap
	 * @return
	 */
	public List<Tag> getTagsWithMostQuestions(Map<Tag, Long> myMap) {
		List<Tag> tags = new ArrayList<>();
		Map<Tag, Long> sorted = myMap.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		for (var entry : sorted.entrySet()) {
			tags.add(entry.getKey());
		}

		return tags.stream()
				.limit(20)
				.collect(Collectors.toList());
	}
}
		
	


