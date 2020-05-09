package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "question")

public class Question {
	
	// UUID for every question
	@Id
	private String id;
	
	// the question asked by any person
	private String header;

	// a more detailed description of the question asked
	private String content;
	
	//private User frageSteller;
	
	// keywords for this question
	private String[] tags;

	private Long timeStamp;
	
	public Question(String header,String content,String[] tags, Long timeStamp) {
		this.header = header;
		this.content = content;
		this.tags = tags;
		this.timeStamp = Instant.now().getEpochSecond();
	}
	
	
}
