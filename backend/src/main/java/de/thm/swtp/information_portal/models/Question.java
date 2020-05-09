package de.thm.swtp.information_portal.models;

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
	
	@Id
	private String id;
	
	private String header;
	
	private String content;
	
	//private User frageSteller;
	
	private String[] tags;
	private Long timeStamp;
	
	public Question(String header,String content,String[] tags, Long timeStamp) {
		this.header = header;
		this.content = content;
		this.tags = tags;
		this.timeStamp = timeStamp;
	}
	
	public Question(String id) {
		this.id = id;
	}
}
