package de.thm.swtp.information_portal;

import de.thm.swtp.information_portal.models.Answer.UpdateAnswer;
import de.thm.swtp.information_portal.models.Comment.UpdateComment;
import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.User.User;

public class Util {
    public static boolean checkQuestionModel(Question questionToCheck){

        return false;
    }

    public static boolean checkUpdateAnswerModel(UpdateAnswer updateAnswer){
        if(updateAnswer != null){
            if(updateAnswer.getId() != null &&
               updateAnswer.getAnswerId() != null){
                return true;
            }
        }
        return false;
    }

    public static boolean checkUpdateCommentModel(UpdateComment updateComment){
        if(updateComment != null){
            if(updateComment.getId() != null &&
                    updateComment.getCommentId() != null){
                return true;
            }
        }
        return false;
    }

    public static boolean checkUserModel(User userToCheck){
        if(userToCheck == null) {
            return false;
        }

        if( userToCheck.getId() == null ||
                userToCheck.getName() == null ||
                userToCheck.getEmail() == null ||
                userToCheck.getPreferred_username() == null
        ){
            return false;
        }


        if(userToCheck.getId().isEmpty() ||
                userToCheck.getName().isEmpty() ||
                userToCheck.getEmail().isEmpty() ||
                userToCheck.getPreferred_username().isEmpty()
        ){
            return false;
        }
        return true;
    }
}