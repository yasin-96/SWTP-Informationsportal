package de.thm.swtp.information_portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.Tag;
import de.thm.swtp.information_portal.service.TagService;

@RestController
@RequestMapping("/api")
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/checkTag/{tagToBeChecked}")
	public Tag checkTag(@PathVariable String tagToBeChecked) {
		Tag tag = tagService.checkIfTagExists(tagToBeChecked);
		return tag;
	}
}
