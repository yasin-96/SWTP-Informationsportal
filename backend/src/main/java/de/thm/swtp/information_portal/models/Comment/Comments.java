package de.thm.swtp.information_portal.models.Comment;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor

//Name of collection in MongoDB
@Document(collection = "comment")
public class Comments {

    private String id;

    // All Comments
    private List<Comment> comments;

    // Creation date as Unix-Timestamp
    private long timestamp;

    public Comments(List<Comment> comments, String answerId) {
        this.id = answerId;
        this.comments = comments;
        this.timestamp = Instant.now().getEpochSecond() * 1000;
    }
}