// package de.thm.swtp.information_portal.controller;

// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
// import org.springframework.web.bind.annotation.*;

// import de.thm.swtp.information_portal.models.Answer;
// import de.thm.swtp.information_portal.models.Answers;
// import de.thm.swtp.information_portal.service.AnswerService;

// import javax.validation.Valid;

// import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

// @RestController
// @RequestMapping("/api")
// public class Auth {

//     @GetMapping("/user")
//     public String checkAuth(@AuthenticationPrincipal Jwt jwt) {
//         return jwt.getClaims().toString();
//     }

// }
