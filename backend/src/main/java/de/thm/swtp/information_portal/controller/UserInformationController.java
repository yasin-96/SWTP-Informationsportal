package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.Question;
import de.thm.swtp.information_portal.models.User;
import de.thm.swtp.information_portal.models.UserInformation;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserInformationRepository;
import de.thm.swtp.information_portal.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class UserInformationController {
    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/{user}/info")
    public CompletableFuture<ResponseEntity<UserInformation>> getUserInfo(@PathVariable String user) throws URISyntaxException {
        Optional<UserInformation> userInfo = userInformationService.getUserInfo(user);
        if(userInfo.isPresent()){
            ResponseEntity<UserInformation> reqUserInfo = userInfo.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            return CompletableFuture.completedFuture(reqUserInfo);
        }
        else {
            List<Question> allQuestions = questionRepository.findAll();
            List<Question> questionsOfUser = new ArrayList<>();
            for(Question question: allQuestions){
                if(question.getUserId().equals(user)){
                    questionsOfUser.add(question);
                }
            }
            UserInformation newUserInfo = new UserInformation(user,questionsOfUser.size());
            userInformationService.addUserInfo(newUserInfo);
            return CompletableFuture
                    .completedFuture(ResponseEntity.created(new URI("/info-portal/api" + newUserInfo.getUserId())).body(newUserInfo));
        }
    }
}
