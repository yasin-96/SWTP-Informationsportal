package de.thm.swtp.information_portal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseUser {

    //Nickname of User
    String userName;

    public ResponseUser(String userName) {

        this.userName = userName;
    }
}
