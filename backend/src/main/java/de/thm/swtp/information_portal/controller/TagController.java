package de.thm.swtp.information_portal.controller;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;
import de.thm.swtp.information_portal.service.TagService;
import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class TagController {

	@Autowired
	private TagService tagService;

	/**
	 * Search a tag
	 * @param tagsToBeChecked The tag
	 * @return founded tag or null
	 */
	@Async
	@GetMapping("/tag/check/{tagsToBeChecked}")
	public CompletableFuture<ResponseEntity<Tag>> checkTags(@PathVariable String tagsToBeChecked) {

		if(tagsToBeChecked != null){
			return CompletableFuture.completedFuture(tagService.getTagByName(tagsToBeChecked));
		}

		return CompletableFuture.completedFuture(
				ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(null)
		);
	}

	/**
	 * Find all tags from database
	 * @return All Tags from Database
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/tag/all")
	public CompletableFuture<List<Tag>> getAllTags() {
		return CompletableFuture.completedFuture(tagService.getAllTags());
	}

	/**
	 * Find all question that has the most used tags
	 * @return List of mosted used tags
	 */
	@Async
	@GetMapping("/tag/questions")
	public CompletableFuture<ResponseEntity<List<Tag>>> getTagsWithMostQuestions() {
		return CompletableFuture.completedFuture(tagService.getTagsWithMostQuestions());
	}
}
