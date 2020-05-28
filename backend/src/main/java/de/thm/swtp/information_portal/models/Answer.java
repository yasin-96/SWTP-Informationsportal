package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Answer {
	
	
	private String id;
	//the answer to the question
	private String content;

	//how good was the answer as counter
	private int rating;

	// the given answer to the question based on this id

	//save currentdate in unix timestamp 
	private Long timeStamp;
	
	

	public Answer(String content, int rating) {
		this.id = UUID.randomUUID().toString();
		this.content = content;
		this.rating = rating;
		this.timeStamp = Instant.now().getEpochSecond()*1000;
	}
}
