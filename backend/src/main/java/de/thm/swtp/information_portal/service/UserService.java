package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.ResponseUser;
import de.thm.swtp.information_portal.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param user
     * @return
     */
    public User addUser(User user){
        return userRepository.save(user);
    }

    /**
     *
     * @param jwt           JWT which is getting received from the UserController
     * @return              returns the wanted user
     */
    public ResponseEntity<User> getLoggedInUser(Jwt jwt) throws URISyntaxException {
        var user = userRepository.findById(jwt.getClaimAsString("sub"));

        if (user.isPresent()) {
            return (user.map(response -> ResponseEntity.ok().body(response))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        } else {
            return saveUserIfNotExists(jwt);
        }
    }


    public ResponseEntity<ResponseUser> getUserById(String id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            ResponseUser responseUser = new ResponseUser(user.get().getPreferred_username());
            return (new ResponseEntity<ResponseUser>(responseUser, HttpStatus.OK));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     *
     * @param jwt
     * @return
     * @throws URISyntaxException
     */
    @Async
    ResponseEntity<User> saveUserIfNotExists(@AuthenticationPrincipal Jwt jwt) throws URISyntaxException {
        if(jwt != null){
            String userId = jwt.getClaimAsString("sub");

            User newUser = new User(userId, jwt.getClaimAsString("name"), jwt.getClaimAsString("email"), jwt.getClaimAsString("preferred_username"));
            addUser(newUser);
            return ResponseEntity.created(new URI("/api/user" + newUser.getId())).body(newUser);
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(null);
    }

}

