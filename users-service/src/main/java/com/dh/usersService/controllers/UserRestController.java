package com.dh.usersService.controllers;

import com.dh.usersService.service.UserService;
import com.dh.usersService.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService UserService;

    @GetMapping("/id/{id}")
    public UserDTO findById(@PathVariable String id){
        return UserService.findbyId(id);
    }


}