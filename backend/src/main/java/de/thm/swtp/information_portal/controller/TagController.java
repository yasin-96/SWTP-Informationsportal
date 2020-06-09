package de.thm.swtp.information_portal.controller;

// das ist ein Test

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.repositories.TagRepository;
import de.thm.swtp.information_portal.service.TagService;

@RestController
@RequestMapping("/api")
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
	public CompletableFuture<Tag> checkTags(@PathVariable String tagsToBeChecked)  throws InterruptedException{
		return CompletableFuture.completedFuture( tagRepository.findByName(tagsToBeChecked));
	}
	
	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	@GetMapping("/getAllTags")
	public CompletableFuture<List<Tag>> getAllTags() {
		return CompletableFuture.completedFuture( tagService.getAllTags());
	}
}	
