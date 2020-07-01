package de.thm.swtp.information_portal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "userInfo")
@NoArgsConstructor
public class UserInformation {

    String userId;

    int numberOfQuestions;

    public UserInformation(String userId, int numberOfQuestions){
        this.userId=userId;
        this.numberOfQuestions=numberOfQuestions;
    }




}
