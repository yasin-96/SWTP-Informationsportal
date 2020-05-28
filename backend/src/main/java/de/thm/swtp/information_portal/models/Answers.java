package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Document(collection ="answer")
public class Answers {

    // UUID for every answer
	
    private String id;
    private List<Answer> listOfAnswers;

    //save currentdate in unix timestamp 
	private Long timeStamp;

    public Answers(List<Answer> listWithAnswers, String questionId) {
        this.id = questionId;
        this.listOfAnswers = listWithAnswers;
        this.timeStamp = Instant.now().getEpochSecond();
    }
}