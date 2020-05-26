package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Data
@Getter
@AllArgsConstructor
@Setter
@Document(collection = "tag")
public class Tag {
	
	@Id
	private String id;
	
	private String name;
	
	private Long timeStamp;

	public Tag(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.timeStamp = Instant.now().getEpochSecond()*1000;
	}
}
