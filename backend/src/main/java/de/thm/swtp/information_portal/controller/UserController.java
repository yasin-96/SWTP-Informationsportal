package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.User.ResponseUser;
import de.thm.swtp.information_portal.models.User.User;
import de.thm.swtp.information_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/info-portal/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * @param jwt
     * @return
     * @throws URISyntaxException
     */
    @Async
    @GetMapping("/user/info")
    CompletableFuture<ResponseEntity<User>> getLoggedInUser(@AuthenticationPrincipal Jwt jwt) throws URISyntaxException {

        var user = new User(
            jwt.getClaimAsString("sub"),
            jwt.getClaimAsString("name"),
            jwt.getClaimAsString("email"),
            jwt.getClaimAsString("preferred_username")
        );

        return CompletableFuture.completedFuture(userService.getLoggedInUser(user.getId(), user));

    }

    /**
     *
     * @param id
     * @return
     */
    @Async
    @PostMapping("/user/name")
    CompletableFuture<ResponseEntity<ResponseUser>> getNameFromId(@RequestBody UUID id) {
        return CompletableFuture.completedFuture(userService.getUserById(id.toString()));
    }
}
