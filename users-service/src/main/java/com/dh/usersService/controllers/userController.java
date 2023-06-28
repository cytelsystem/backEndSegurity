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

  @GetMapping("/user")
  public String hello() {
    return "hello gateway con yml";
  }

//  @GetMapping("/all")
//  public ResponseEntity<List<Bill>> getAll() {
//
//    return ResponseEntity.ok().body(IFeignMsBills.getAll().getBody());
//  }

//  @GetMapping("/all")
//  public ResponseEntity<List<Bill>> getAll() {
//
//    return ResponseEntity.ok().body(BillService.getAll());
//  }


  @GetMapping("/facturas/{idBill}")
  public ResponseEntity<List<UserDTO>> getAllfacturasbyuser(@PathVariable String idBill) {
    return ResponseEntity.ok().body(Collections.singletonList(BillService.findUserBillsById(idBill)));
  }

//  @PostMapping("/crear")
//  public ResponseEntity<String> crear(@RequestBody Bill o){
//
//    return BillService.guardar(o);
//  }



}
