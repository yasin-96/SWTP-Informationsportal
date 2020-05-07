package de.thm.swtp.information_portal.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "questions")
public class Question {
	
	@Id
	private String id;
	
	private String header;
	
	private String content;
	
	//private User frageSteller;
	
	private String[] tags;
	
	//private Antwort[] antworten;
	
	private Long timeStamp;
	
	public Question(String header,String content,String[] tags, Long timeStamp) {
		this.header = header;
		this.content = content;
		this.tags = tags;
		this.timeStamp = timeStamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
	
}
