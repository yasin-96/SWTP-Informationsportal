package de.thm.swtp.information_portal.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Getter
@Setter
@Document(collection = "userInfo")
public class UserInformation {

    private String id;
    private int numberOfQuestions;
    private int numberOfAnswers;

    /**
     * @param id
     * @param numberOfQuestions
     * @param numberOfAnswers
     */
    public UserInformation(String id, int numberOfQuestions, int numberOfAnswers) {
        this.id = id;
        this.numberOfQuestions = numberOfQuestions;
        this.numberOfAnswers = numberOfAnswers;
    }
}
