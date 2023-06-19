package com.dh.userservice.controller;

import com.dh.userservice.model.User;
import com.dh.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/todo")
    public ResponseEntity<String> todo() {

        return ResponseEntity.ok("Hello Anonymous");
    }

    @GetMapping("/find/{id}")
    public User findById(@PathVariable Integer id){

        return userService.findById(id);
    }
}



