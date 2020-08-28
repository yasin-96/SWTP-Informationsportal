package de.thm.swtp.information_portal.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;

@NoArgsConstructor
@Data
@Getter
@Setter
public class SocketResponse {

    private String questionId;
    private String answerId;

    private HashSet<User> listOfUsers;
    private String headerOfQuestion;

    private Boolean isAnswer = false;
    private Boolean isComment = false;

    private MinimalUser minimalUser;

    private long timestamp;

    public SocketResponse(String questionId, HashSet<User> listOfUsers, String headerOfQuestion) {
        this.questionId = questionId;
        this.listOfUsers = listOfUsers;
        this.headerOfQuestion = headerOfQuestion;
        this.timestamp = Instant.now().getEpochSecond() * 1000;
    }

    public SocketResponse(String questionId, HashSet<User> listOfUsers, String headerOfQuestion, Boolean isAnswer, boolean isComment, MinimalUser minimalUser) {
        this(questionId, listOfUsers, headerOfQuestion);
        this.isAnswer = isAnswer;
        this.isComment = isComment;
        this.minimalUser = minimalUser;
    }

    public SocketResponse(String questionId, String answerId,HashSet<User> listOfUsers, String headerOfQuestion, Boolean isAnswer, boolean isComment, MinimalUser minimalUser) {
        this(questionId,listOfUsers, headerOfQuestion);
        this.answerId = answerId;
        this.isAnswer = isAnswer;
        this.isComment = isComment;
        this.minimalUser = minimalUser;
    }
}
