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
            return new ResponseEntity(user, HttpStatus.OK);
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
        return new ResponseEntity(HttpStatus.NOT_FOUND);
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
        return new ResponseEntity(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }
}

