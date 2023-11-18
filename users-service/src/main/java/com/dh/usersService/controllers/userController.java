package com.dh.usersService.controllers;


import com.dh.usersService.feignInterface.IFeignMsBills;
import com.dh.usersService.model.Bill;
import com.dh.usersService.model.UserDTO;
import com.dh.usersService.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class userController {

  @Autowired
  private BillService BillService;
  @Autowired
  private IFeignMsBills IFeignMsBills;



  @GetMapping("/facturas1/{idBill}")
  public String obtenerFactura(@PathVariable Long idBill) {
    // Puedes hacer operaciones con idBill si es necesario
    // En este caso, simplemente retornamos un saludo como prueba
    return "Hola desde la factura con ID: " + idBill;
  }



  @GetMapping("/facturas/{idBill}")
  public ResponseEntity<List<UserDTO>> getAllfacturasbyuser(@PathVariable String idBill) {
    return ResponseEntity.ok().body(Collections.singletonList(BillService.findUserBillsById(idBill)));
  }



}
