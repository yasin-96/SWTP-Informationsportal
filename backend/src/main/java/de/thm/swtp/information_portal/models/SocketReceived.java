package de.thm.swtp.information_portal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class SocketReceived {
    private String questionId;
    private Boolean isAnswer;
    private Boolean isComment;

    //user that has post new answer/comment 
    private MinimalUser minimalUser;

    public SocketReceived(String questionId, Boolean isAnswer, Boolean isComment, MinimalUser minimalUser) {
        this.questionId = questionId;
        this.isAnswer = isAnswer;
        this.isComment = isComment;
        this.minimalUser = minimalUser;
    }
}