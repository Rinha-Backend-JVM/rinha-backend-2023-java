package com.people.peoplemanager.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PeopleController {
    @PostMapping
    public ResponseEntity add() {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public List<String> search() {
        return Collections.emptyList();
    }

    @GetMapping("/{id}")
    public String get(@PathVariable String id) {
        return "Hi, I'm person " + id;
    }
}
