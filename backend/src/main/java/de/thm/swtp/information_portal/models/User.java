package de.thm.swtp.information_portal.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "user")
public class User {

    // Jwt.claims(Sub)

    private String id;

    // Fullname of Person
    private String name;

    private String email;

    // Nickname
    private String preferred_username;

    // Creation Date as Unix-Timestamp
    private Long timestamp;

    public User(String sub, String name,String email,
            String preferred_username) {
        this.id = sub;
        this.name = name;
        this.email = email;
        this.preferred_username = preferred_username;
        this.timestamp = Instant.now().getEpochSecond() * 1000;
    }
}
