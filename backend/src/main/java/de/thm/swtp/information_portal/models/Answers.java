package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok feature
@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor

//Name of collection in MongoDB
@Document(collection = "answer")

public class Answers {

    // UUID for every answer
    private String id;

    // All answers of one question
    private List<Answer> listOfAnswers;

    // save currentdate in unix timestamp
    private Long timeStamp;

    public Answers(List<Answer> listWithAnswers, String questionId) {
        this.id = questionId;
        this.listOfAnswers = listWithAnswers;
        this.timeStamp = Instant.now().getEpochSecond() *1000;
    }
}