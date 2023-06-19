package com.dh.msbills.controllers;

import com.dh.msbills.models.Bill;
import com.dh.msbills.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Bill>> getAll() {
        return ResponseEntity.ok().body(service.getAllBill());
    }

    @GetMapping("/all1")
    @PreAuthorize("hasRole('ROLE_GUEST')")
    public String withRole() {
        return "Only for User role";
    }

}
