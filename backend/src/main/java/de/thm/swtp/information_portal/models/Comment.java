package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Comment {

    private String id;
    private String content;
    private String userName;
    private int rating;
    private long timestamp;

    public Comment(String content, String userName, int rating) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.userName = userName;
        this.rating = rating;
        this.timestamp = Instant.now().getEpochSecond()*1000;
    }
}