package de.thm.swtp.information_portal.controller;

// das ist ein Test

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*")
public class TagController {

	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/checkTags/{tagsToBeChecked}")
	public Tag checkTags(@PathVariable String tagsToBeChecked) {
		 return tagRepository.findByName(tagsToBeChecked);
	}
	
	@GetMapping("/getAllTags")
	public List<Tag> getAllTags() {
		return tagService.getAllTags();
	}
}	
