package com.people.peoplemanager.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PeopleController {
    private String abc;
    @PostMapping
    public ResponseEntity add() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
