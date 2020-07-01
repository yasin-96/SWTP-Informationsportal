package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.Question;
import org.apache.catalina.realm.AuthenticatedUserRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import de.thm.swtp.information_portal.models.User;
import de.thm.swtp.information_portal.repositories.UserRepository;
import de.thm.swtp.information_portal.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Async
    @GetMapping("/user/{sub}")
    CompletableFuture<ResponseEntity<User>> getUser(@AuthenticationPrincipal Jwt jwt,@PathVariable String sub){
        Optional<User> user = userService.getUser(sub);
        ResponseEntity<User> reqUser = user.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return CompletableFuture.completedFuture(reqUser);
    }


    @Async
    @PostMapping("/user")
    CompletableFuture<ResponseEntity<User>> saveUserIfNotExists(@AuthenticationPrincipal Jwt jwt) throws URISyntaxException {
        String userId = jwt.getClaimAsString("sub");
        Optional<User> user = userService.getUser(userId);
        if(user.isPresent()){
            ResponseEntity<User> reqUser = user.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            return CompletableFuture.completedFuture(reqUser);
        }
        else{
            User newUser = new User(userId,jwt.getClaimAsString("name"),jwt.getClaimAsString("email"),jwt.getClaimAsString("preferred_username"));
            userService.addUser(newUser);
            return CompletableFuture
                    .completedFuture(ResponseEntity.created(new URI("/api/user" + newUser.getId())).body(newUser));
        }
    }

}
