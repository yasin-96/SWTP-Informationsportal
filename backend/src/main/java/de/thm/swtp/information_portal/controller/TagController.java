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

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class TagController {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TagService tagService;

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
	@GetMapping("/tag/getAllTags")
	public CompletableFuture<List<Tag>> getAllTags() {
		return CompletableFuture.completedFuture(tagService.getAllTags());
	}

	/**
	 *
	 * @param tag
	 * @return
	 */
	@Async
	@PostMapping("/tag/newTag")
	public Tag createTag(@RequestBody Tag tag) {
		//TAG prüfen??
		//Wenn das tag schon vorhanden ist brauchen wir es doch nicht mehr speichern oder?
		return tagRepository.save(tag);
	}

	/**
	 *
	 * @return
	 */
	@Async
	@GetMapping("/tagsWithMostQuestions")
	public CompletableFuture<ResponseEntity<List<Tag>>> getTagsWithMostQuestions() {
		return CompletableFuture.completedFuture(tagService.getTagsWithMostQuestions());
	}


}
