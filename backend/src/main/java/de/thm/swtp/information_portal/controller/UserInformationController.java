package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.*;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.UserInformationRepository;
import de.thm.swtp.information_portal.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;

    /**
     *
     * @param userId
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
