package de.thm.swtp.information_portal.controller;

import com.google.common.net.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "info-portal")
public class IndexController {

    @GetMapping
    public ResponseEntity<Void> index() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/info-portal/index.html")
                .build();
    }

}