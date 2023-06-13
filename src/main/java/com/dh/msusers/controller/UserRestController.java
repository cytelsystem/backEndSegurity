package com.dh.msusers.controller;

import com.dh.msusers.modelDTO.UserDTO;
import com.dh.msusers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService UserService;

    @GetMapping("/firstname/{firsName}")
    public List<UserDTO> findByFirstName(@PathVariable String firsName){

        return UserService.findByFirsName(firsName);
    }

    @GetMapping("/id/{id}")
    public UserDTO findById(@PathVariable String id){
        return UserService.findbyId(id);
    }

    @PutMapping("/update")
    public UserDTO findById(@RequestParam String id, @RequestParam String nationality){
        return UserService.updateNationality(id, nationality);
    }



}
