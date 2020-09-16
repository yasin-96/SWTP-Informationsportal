package de.thm.swtp.information_portal.models.Answer;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Getter
@Setter
public class UpdateAnswer {
    private String id;

    private String answerId;
    // the answer to the question
    private String content;

    private int rating = -1;

    public UpdateAnswer(String id, String answerId, String content){
        this.id = id;
        this.answerId = answerId;
        this.content = content;
    }

    public UpdateAnswer(String id, String answerId, int rating){
        this.id = id;
        this.answerId = answerId;
        this.rating = rating;
    }
}
