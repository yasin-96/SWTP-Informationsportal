package de.thm.swtp.information_portal.models;

import com.mongodb.util.Hash;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@NoArgsConstructor
public class SocketResponse {

    private String questionId;

    private HashSet<User> listOfUsers;
    private String headerOfQuestion;

    private Boolean isAnswer; 
    private Boolean isComment; 

    private long timestamp;

    public SocketResponse(HashSet<User> listOfUsers, String headerOfQuestion){
       this.listOfUsers = listOfUsers;
       this.headerOfQuestion = headerOfQuestion;
    }
}
