package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.ResponseUser;
import de.thm.swtp.information_portal.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

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
     * @param userId
     * @param user
     * @return returns the wanted user
     * @throws URISyntaxException
     */
    public ResponseEntity<User> getLoggedInUser(String userId, User user) throws URISyntaxException {
        if(userRepository.existsById(userId)){
            return ResponseEntity.ok().body(user);
        }
        return saveUserIfNotExists(user);
    }


    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<ResponseUser> getUserById(String id){
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            var responseUser = new ResponseUser(user.get().getPreferred_username());
            return new ResponseEntity(responseUser, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     *
     * @param user
     * @return
     * @throws URISyntaxException
     */
    @Async
    ResponseEntity<User> saveUserIfNotExists(User user) throws URISyntaxException {
        if(user != null){
            addUser(user);
            return ResponseEntity
                    .created(new URI("/api/user" + user.getId()))
                    .body(user);
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(null);
    }
}

