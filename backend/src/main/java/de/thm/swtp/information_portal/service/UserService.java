package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.User.ResponseUser;
import de.thm.swtp.information_portal.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;

import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param userId
     * @param user
     * @return returns the wanted user
     * @throws URISyntaxException
     */
    public ResponseEntity<User> getLoggedInUser(String userId, User user) throws URISyntaxException {
        if (userRepository.existsById(userId)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);
        }
        return saveUserIfNotExists(user);
    }


    /**
     * @param id
     * @return
     */
    public ResponseEntity<ResponseUser> getUserById(String id) {
        var user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ResponseUser(
                                user.get().getPreferred_username()
                        )
                );
    }

    /**
     * @param user
     * @return
     */
    public ResponseEntity<User> saveUserIfNotExists(User user) {
        if (user != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userRepository.save(user));
        }
        return ResponseEntity
                .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                .body(null);
    }
}

