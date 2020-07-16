package de.thm.swtp.information_portal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "userInfo")
@NoArgsConstructor
public class UserInformation {

    //String userId;

    String _id;
    int numberOfQuestions;
    int numberOfAnswers;

    public UserInformation(String id, int numberOfQuestions,int numberOfAnswers){
        this._id=id;
        this.numberOfQuestions=numberOfQuestions;
        this.numberOfAnswers = numberOfAnswers;
    }




}
