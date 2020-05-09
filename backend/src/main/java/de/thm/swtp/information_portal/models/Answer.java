package de.thm.swtp.information_portal.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(collection ="answer")
public class Answer {
	
	@Id
	private String id;
	private String content;
	private int rating;
	
	private String question;
	//private Question questionId
	private Long timeStamp;
	//private List<Answer> comments;
	
	public Answer(String content, int rating,Long timeStamp,String question) {
		this.content = content;
		this.rating = rating;
		this.timeStamp = timeStamp;
		this.question = question;
	}
	
}
