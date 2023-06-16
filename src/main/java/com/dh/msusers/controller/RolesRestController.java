package com.dh.msusers.controller;



import com.dh.msusers.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesRestController {
    @Autowired
    private RolesService RolesService;

    @PostMapping("/crear")
    public ResponseEntity<String> createRoles(@RequestParam String roles) {
        String createdRoles = RolesService.createRoles(roles);
        return ResponseEntity.ok(createdRoles);
    }


}
