package com.dh.usersService.controllers;

import com.dh.usersService.service.UserService;
import com.dh.usersService.model.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService UserService;

    @PostMapping("/crear")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        UserDTO createdUser = UserService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/id/{id}")
    public UserDTO findById(@PathVariable String id){

        return UserService.findbyId(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        UserDTO user = UserService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDTO>> findByFirstName(@PathVariable String name) {
        return ResponseEntity.ok().body(UserService.findByFirstName(name));
    }



}