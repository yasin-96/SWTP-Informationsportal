package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.User.ResponseUser;
import de.thm.swtp.information_portal.models.User.User;
import de.thm.swtp.information_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * Returns the data of a user. First and last name, email and the personal username
     * @param jwt User access credentials
     * @return
     * @throws URISyntaxException
     */
    @Async
    @GetMapping("/user/info")
    CompletableFuture<ResponseEntity<User>> getLoggedInUser(@AuthenticationPrincipal Jwt jwt) throws URISyntaxException {

        if(jwt == null) {
            return CompletableFuture.completedFuture(
                    ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(null)
            );
        }

        var user = new User(
            jwt.getClaimAsString("sub"),
            jwt.getClaimAsString("name"),
            jwt.getClaimAsString("email"),
            jwt.getClaimAsString("preferred_username")
        );

        return CompletableFuture.completedFuture(userService.getLoggedInUser(user.getId(), user));

    }

    /**
     * Returns the username based on the UserId
     * @param id Id of User
     * @return
     */
    @Async
    @GetMapping("/user/name/{id}")
    CompletableFuture<ResponseEntity<ResponseUser>> getNameFromId(@PathVariable UUID id) {
        return CompletableFuture.completedFuture(userService.getUserById(id.toString()));
    }
}
