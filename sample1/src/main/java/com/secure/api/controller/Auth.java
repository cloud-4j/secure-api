package com.secure.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//curl -X POST http://localhost:8080/login -H "Content-Type: application/json" -d '{"username":"bob", "password":"1234"}'

@RestController
@RequestMapping("/login")
public class Auth {

    @PostMapping
    public ResponseEntity<?> save(Authentication authentication) {
        System.out.println("I am here");
//        UserEntity userEntity = userService.save(user);
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
//                .buildAndExpand(userEntity.getUsername()).toUriString());
        return ResponseEntity.noContent().build();
    }
}
