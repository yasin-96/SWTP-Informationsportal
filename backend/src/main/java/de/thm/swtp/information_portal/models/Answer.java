package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.List;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
// @Document(collection ="answer")

public class Answer {
	
	// UUID for every answer
	// @Id
	// private String id;

	//the answer to the question
	private String content;

	//how good was the answer as counter
	private int rating;

	// the given answer to the question based on this id
	// private String question;

	private List<Comment> comments;


	//save currentdate in unix timestamp 
	private Long timeStamp;
	
	public Answer(String content, List<Comment> comments, int rating, String question) {
		this.content = content;
		this.rating = rating;
		// this.question = question;
		this.comments = comments;
		this.timeStamp = Instant.now().getEpochSecond();
	}
}
