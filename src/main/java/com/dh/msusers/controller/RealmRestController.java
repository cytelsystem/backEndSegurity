package com.dh.msusers.controller;


import com.dh.msusers.services.RealmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realm")
public class RealmRestController {
    @Autowired
    private RealmService RealmService;
    @PostMapping("/crear")
    public ResponseEntity<String> createRealm(@RequestParam String realm) {
        String createdRealm = RealmService.createRealm(realm);
        return ResponseEntity.ok(createdRealm);
    }


}
