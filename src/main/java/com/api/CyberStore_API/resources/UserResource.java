package com.api.CyberStore_API.resources;

import com.api.CyberStore_API.entities.User;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<User> findAll(){
        User u = new User(1L, "Evandro", "evandro@gmail.com","123456789");
        return ResponseEntity.ok().body(u);



    }
}
