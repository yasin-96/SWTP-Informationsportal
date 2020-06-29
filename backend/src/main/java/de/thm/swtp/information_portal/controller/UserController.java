package de.thm.swtp.information_portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.swtp.information_portal.models.User;
import de.thm.swtp.information_portal.repositories.UserRepository;
import de.thm.swtp.information_portal.service.UserService;


import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Async
    @GetMapping("/auth")
    public CompletableFuture<Optional<User>> checkTags(@AuthenticationPrincipal Jwt jwt) throws InterruptedException {
        return CompletableFuture.completedFuture(userRepository.findById(jwt.getClaim("sub")));
    }

}
