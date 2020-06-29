package de.thm.swtp.information_portal.service;

import org.springframework.beans.factory.annotation.Autowired;

import de.thm.swtp.information_portal.repositories.UserRepository;

public class UserService {
    
    @Autowired
    private UserRepository userRepository;
}