package de.thm.swtp.information_portal.controller;

import com.google.common.net.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/info-portal")
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "index";
    }
}