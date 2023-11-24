package com.dh.usersService.controllers;

import lombok.RequiredArgsConstructor;
import com.dh.usersService.model.UserToGrup;
import com.dh.usersService.service.UserService;
import com.dh.usersService.model.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @CrossOrigin(origins = "*")
    @PostMapping("/adicionargrupo")
     public ResponseEntity<UserToGrup> addUserToGroup(@RequestBody UserToGrup data) {
         UserToGrup addUserGrup = UserService.addUserToGroup(data);
         return ResponseEntity.ok(addUserGrup);
     }


//    public ResponseEntity<String> addUserToGroup(@RequestBody UserToGrup data) {
//        String mensaje = "Hola, se ha procesado: " + data.toString();
//        return ResponseEntity.ok(mensaje);
//    }


    @CrossOrigin(origins = "*")
    @PostMapping("/crear")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        UserDTO createdUser = UserService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDTO>> findByFirstName(@PathVariable String name) {
        return ResponseEntity.ok().body(UserService.findByFirstName(name));
    }



}