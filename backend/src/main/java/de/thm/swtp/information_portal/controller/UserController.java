package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.ResponseUser;
import de.thm.swtp.information_portal.models.User;
import de.thm.swtp.information_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/user")
    CompletableFuture<ResponseEntity<User>> getUser(@AuthenticationPrincipal Jwt jwt) throws URISyntaxException {
        Optional<User> user = userService.getUser(jwt.getClaimAsString("sub"));
        if (user.isPresent()) {
            ResponseEntity<User> reqUser = user.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            return CompletableFuture.completedFuture(reqUser);
        } else {
            return saveUserIfNotExists(jwt);
        }
    }


    @Async
    @PostMapping("/userNameFromId")
    CompletableFuture<ResponseEntity<ResponseUser>> getNameFromId(@RequestBody String id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            ResponseUser responseUser = new ResponseUser(user.get().getPreferred_username());
            return CompletableFuture.completedFuture(new ResponseEntity<ResponseUser>(responseUser, HttpStatus.OK));
        }
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
    }


    @Async
    CompletableFuture<ResponseEntity<User>> saveUserIfNotExists(@AuthenticationPrincipal Jwt jwt) throws URISyntaxException {
        if(jwt != null){
            String userId = jwt.getClaimAsString("sub");

            User newUser = new User(userId, jwt.getClaimAsString("name"), jwt.getClaimAsString("email"), jwt.getClaimAsString("preferred_username"));
            userService.addUser(newUser);
            return CompletableFuture
                    .completedFuture(ResponseEntity.created(new URI("/api/user" + newUser.get_Id())).body(newUser));
        }
        return CompletableFuture
                .completedFuture(ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(null));
    }

}
