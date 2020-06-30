package de.thm.swtp.information_portal.controller;

import org.apache.catalina.realm.AuthenticatedUserRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.User;
import de.thm.swtp.information_portal.repositories.UserRepository;
import de.thm.swtp.information_portal.service.UserService;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private UserService userService;

    @Async
    @GetMapping("/user")
    public CompletableFuture<ResponseEntity<String>> checkTags(@AuthenticationPrincipal Jwt jwt) throws InterruptedException {

        //TODO 
        // if( user neu -> anlegen) {

        // } else {
        //     user holen aus der Datenbank anhand der sub id
        //     //jwt.getClaimAsString("sub");
        // }

        System.out.println("JWT: " +jwt);
        // return CompletableFuture.completedFuture(userRepository.findById(jwt.getClaim("sub")));
        return CompletableFuture.completedFuture(new ResponseEntity<>("", HttpStatus.OK));
    }

}
