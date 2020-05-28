package de.thm.swtp.information_portal.models;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Document(collection = "comments")
public class Comments {
    
    
    private String id;
    
    private List<Comment> comments;
    private long timestamp;


    public Comments(List<Comment> comments, String answerId) {
        this.id = answerId;
        this.comments = comments;
        this.timestamp = Instant.now().getEpochSecond();
    }
}