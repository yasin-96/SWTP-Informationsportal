package de.thm.swtp.information_portal.models;

import com.mongodb.util.Hash;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
public class SocketResponse {
    private HashSet<User> listOfUsers;
    private String headerOfQuestion;


    public SocketResponse(HashSet<User> listOfUsers, String headerOfQuestion){
       this.listOfUsers = listOfUsers;
       this.headerOfQuestion = headerOfQuestion;
    }
}
