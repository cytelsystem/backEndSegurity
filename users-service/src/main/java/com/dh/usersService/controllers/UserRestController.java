package com.dh.usersService.controllers;

import com.dh.usersService.service.UserService;
import com.dh.usersService.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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




}