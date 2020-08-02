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

    //UUID of the Question
    private String questionId;

    //UUID of the Answer
    private String answerId;

    private Boolean isAnswer;
    private Boolean isComment;

    //User that has post new answer/comment
    private MinimalUser minimalUser;

    public SocketReceived(String questionId, String answerId, Boolean isAnswer, Boolean isComment, MinimalUser minimalUser) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.isAnswer = isAnswer;
        this.isComment = isComment;
        this.minimalUser = minimalUser;
    }
}