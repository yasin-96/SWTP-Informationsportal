package de.thm.swtp.information_portal.models.Comment;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private String id;

    // Content of commet
    private String content;

    // User that has written this comment
    private String userId;

    private String userName;

    // Likes as counter
    private int rating;

    // Creation Date as Unix-Timestamp
    private long timestamp;

    public Comment(String content, String userId, String userName, int rating) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.timestamp = Instant.now().getEpochSecond() * 1000;
    }
}