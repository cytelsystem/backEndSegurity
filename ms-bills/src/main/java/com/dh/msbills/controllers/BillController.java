package com.dh.msbills.controllers;

import com.dh.msbills.models.Bill;
import com.dh.msbills.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    @Autowired
    private final BillService service;



    @PostMapping("/crear")
    @PreAuthorize("hasAuthority('GROUP_PROVIDERS')")
    public ResponseEntity<String> crear(@RequestBody Bill o){
        ResponseEntity<String> respuesta = null;

        if(service.guardar(o) != null){
            respuesta = ResponseEntity.ok("El Registro fue creado con Exito");
        }else{
            respuesta = ResponseEntity.internalServerError().body("Ooops");
        }

        return respuesta;
    }



    @GetMapping("/facturasId")
    public ResponseEntity<List<Bill>> billxid(@RequestParam String customerBill){
        return ResponseEntity.ok().body(service.findByidBill(customerBill));
    }





}
