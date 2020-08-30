package de.thm.swtp.information_portal.models.Comment;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class UpdateComment {
    private String id;

    private String commentId;
    // the answer to the question
    private String content;

    private int rating = -1;

    public UpdateComment(String id, String commentId, String content){
        this.id = id;
        this.commentId = commentId;
        this.content = content;
    }

    public UpdateComment(String id, String commentId, int rating){
        this.id = id;
        this.commentId = commentId;
        this.rating = rating;
    }
}
