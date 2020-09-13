package de.thm.swtp.information_portal;

import de.thm.swtp.information_portal.models.Answer.Answers;
import de.thm.swtp.information_portal.models.Answer.UpdateAnswer;
import de.thm.swtp.information_portal.models.Comment.Comments;
import de.thm.swtp.information_portal.models.Comment.UpdateComment;
import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.User.User;

public class Util {
    public static boolean isNewQuestionValid(Question questionToCheck) {
        if (questionToCheck == null) {
            return false;
        }

        if (questionToCheck.getHeader() == null
                || questionToCheck.getContent() == null
                || questionToCheck.getTags() == null
        ) {
            return false;
        }

        if (questionToCheck.getHeader().isEmpty()
                || questionToCheck.getContent().isEmpty()
                || questionToCheck.getTags().isEmpty()
        ) {
            return false;
        }
        return true;
    }

    public static boolean checkExistQuestionModel(Question questionToCheck) {
        if (questionToCheck == null) {
            return false;
        }

        if (questionToCheck.getId() == null
                || questionToCheck.getHeader() == null
                || questionToCheck.getContent() == null
                || questionToCheck.getUserId() == null
                || questionToCheck.getUserName() == null
                || questionToCheck.getTags() == null
        ) {
            return false;
        }

        if (questionToCheck.getId().isEmpty()
                || questionToCheck.getHeader().isEmpty()
                || questionToCheck.getContent().isEmpty()
                || questionToCheck.getUserId().isEmpty()
                || questionToCheck.getUserName().isEmpty()
                || questionToCheck.getTags().isEmpty()
        ) {
            return false;
        }
        return true;
    }


    public static boolean checkUpdateAnswerModel(UpdateAnswer updateAnswer) {
        if (updateAnswer != null) {
            if (updateAnswer.getId() != null &&
                    updateAnswer.getAnswerId() != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkUpdateCommentModel(UpdateComment updateComment) {
        if (updateComment != null) {
            if (updateComment.getId() != null &&
                    updateComment.getCommentId() != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkUserModel(User userToCheck) {
        if (userToCheck == null) {
            return false;
        }

        if (userToCheck.getName() == null &&
                userToCheck.getEmail() == null &&
                userToCheck.getPreferred_username() == null
        ) {
            return false;
        }


        if (userToCheck.getName().isEmpty() ||
                userToCheck.getEmail().isEmpty() ||
                userToCheck.getPreferred_username().isEmpty()
        ) {
            return false;
        }
        return true;
    }

    public static boolean checkLoggedInUserData(String id, User user) {
        if (id == null || user == null) {
            return false;
        }
        return true;
    }

    public static boolean checkUpdateComment(UpdateComment updateComment) {
        if (updateComment == null) {
            return false;
        }
        if (updateComment.getId() == null
                || updateComment.getCommentId() == null
                || updateComment.getContent() == null
        ) {
            return false;
        }

        if (updateComment.getId().isEmpty()
                || updateComment.getCommentId().isEmpty()
                || updateComment.getContent().isEmpty()
        ) {
            return false;
        }
        return true;
    }

    public static boolean isUpdateAnswerValid(UpdateAnswer updateAnswer) {
        if (updateAnswer == null) {
            return false;
        }
        if (updateAnswer.getId() == null
                || updateAnswer.getAnswerId() == null
                || updateAnswer.getContent() == null
        ) {
            return false;
        }

        if (updateAnswer.getId().isEmpty()
                || updateAnswer.getAnswerId().isEmpty()
                || updateAnswer.getContent().isEmpty()
        ) {
            return false;
        }
        return true;
    }

    public static boolean isNewAnswerValid(Answers answers) {

        if (answers == null) {
            return false;
        }

        if (answers.getListOfAnswers() == null) {
            return false;
        }

        if ( answers.getListOfAnswers().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isNewCommentValid(Comments comments) {

        if (comments == null) {
            return false;
        }

        if (comments.getId() == null || comments.getComments() == null) {
            return false;
        }

        if (comments.getId().isEmpty() || comments.getComments().isEmpty()) {
            return false;
        }
        return true;
    }
}