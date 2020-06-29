package de.thm.swtp.information_portal.models;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User{
	
	//Jwt.claims(Sub) 
    private String id;
    
    //Fullname of Person
    private String name;

    //Firstname
    private String given_name;

    //Lastname
    private String family_name;

    
    private String email;

    //Nickname
	private String preferred_username;

    //Creation Date
	private Long timestamp;
	
    public User(String sub, String name, String given_name, String family_name, String email, String preferred_username ) {
        this.name = name ;
        this.given_name = given_name ;
        this.family_name = family_name ;
        this.email = email ;
        this.preferred_username = preferred_username ;
        this.timestamp = Instant.now().getEpochSecond()*1000;
    }
}
