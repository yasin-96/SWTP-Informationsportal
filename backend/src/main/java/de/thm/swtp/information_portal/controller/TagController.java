package de.thm.swtp.information_portal.controller;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;
import de.thm.swtp.information_portal.service.TagService;
import static java.util.stream.Collectors.toMap;

// import javax.validation.Valid;
@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class TagController {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TagService tagService;

	@Autowired
	private QuestionService questionService;

	/**
	 * 
	 * @param tagsToBeChecked
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/checkTags/{tagsToBeChecked}")
	public CompletableFuture<Tag> checkTags(@PathVariable String tagsToBeChecked) throws InterruptedException {
		return CompletableFuture.completedFuture(tagRepository.findByName(tagsToBeChecked));
	}

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/getAllTags")
	public CompletableFuture<List<Tag>> getAllTags() {
		return CompletableFuture.completedFuture(tagService.getAllTags());
	}

	@Async
	@PostMapping("/tag/newTag")
	public Tag createTag(@RequestBody Tag tag) {
		return tagRepository.save(tag);
	}

	@Async
	@GetMapping("/tagsWithMostQuestions")
	public CompletableFuture<ResponseEntity<List<Tag>>> getTagsWithMostQuestions() {
		List<Question> allQuestions = questionService.getAllQuestions();
		List<Tag> allTags = tagService.getAllTags();
		Map<Tag, Long> map = allQuestions.stream().flatMap(q -> q.getTags().stream()).filter(allTags::contains)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		var mostActiveTags = getTagsWithMostQuestions(map);
		return CompletableFuture.completedFuture(new ResponseEntity<>(mostActiveTags, HttpStatus.OK));
	}

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
