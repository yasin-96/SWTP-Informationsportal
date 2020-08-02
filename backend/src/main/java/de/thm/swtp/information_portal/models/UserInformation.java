package de.thm.swtp.information_portal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor

@Document(collection = "userInfo")
public class UserInformation {

    private String _id;
    private int numberOfQuestions;
    private int numberOfAnswers;

    public UserInformation(String id, int numberOfQuestions, int numberOfAnswers) {
        this._id = id;
        this.numberOfQuestions = numberOfQuestions;
        this.numberOfAnswers = numberOfAnswers;
    }
}
