package de.thm.swtp.information_portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thm.swtp.information_portal.models.Comments;
import de.thm.swtp.information_portal.repositories.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public List<Comments>findAllComments(){
		return commentRepository.findAll();
	}
	
	public Optional<Comments> findByAnswerId(String id) {
		return commentRepository.findById(id);
	}
	
	public Comments postComments(Comments comments) {
		return commentRepository.save(comments);
	}
}
