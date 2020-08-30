package de.thm.swtp.information_portal.controller;

import de.thm.swtp.information_portal.models.Socket.SocketResponse;
import de.thm.swtp.information_portal.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/info-portal/ws")
@CrossOrigin(origins = "*")
public class SocketController {

    @Autowired
    private SocketService socketService;

    /**
     *
     * @param wsMessage             the message for the consumer
     * @return                      returns the SocketResponse object for the wanted type
     */
    @MessageMapping("/hello")
    @SendTo("/notify")
    public CompletableFuture<ResponseEntity<SocketResponse>> socketResponse(@RequestBody String wsMessage) {

        if(wsMessage != null) {
            return CompletableFuture.completedFuture(socketService.createMessage(wsMessage));
        }
        return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.BAD_REQUEST));
    }
}
