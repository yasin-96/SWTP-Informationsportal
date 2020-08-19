package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import de.thm.swtp.information_portal.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * @param id
     * @return
     */
    public Optional<User> getUser(String id){
        return userRepository.findById(id);
    }

}

