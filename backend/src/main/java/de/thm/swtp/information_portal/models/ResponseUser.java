package de.thm.swtp.information_portal.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseUser {
    String name;

    public ResponseUser(String name){
        this.name = name;
    }
}
