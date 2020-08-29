package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor
public class Answer {

	private String id;

	// the answer to the question
	private String content;

	// how good was the answer as counter
	private int rating;

	// User that has written this answer
	private String userId;

	private String userName;

	// save currentdate in unix timestamp
	private Long timeStamp;

	public Answer(String content, int rating, String userId, String userName) {
		this.id = UUID.randomUUID().toString();
		this.content = content;
		this.rating = rating;
		this.userId = userId;
		this.userName = userName;
		this.timeStamp = Instant.now().getEpochSecond() * 1000;
	}
}


