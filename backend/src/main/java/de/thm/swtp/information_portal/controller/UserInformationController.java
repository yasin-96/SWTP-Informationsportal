package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.User.UserInformation;
import de.thm.swtp.information_portal.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;

    /**
     * Returns a user's data showing how many questions and answers were asked and answered
     * @param userId Id of User
     * @return
     * @throws URISyntaxException
     */
    @Async
    @GetMapping("/info/{userId}")
    public CompletableFuture<ResponseEntity<UserInformation>> getUserInfo(@PathVariable UUID userId) throws URISyntaxException {

        return CompletableFuture.completedFuture(
                userInformationService.getUserInfo(userId.toString())
        );
    }
}
