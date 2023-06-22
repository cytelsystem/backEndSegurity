package com.dh.usersService.controllers;


import com.dh.usersService.feignInterface.IFeignMsBills;
import com.dh.usersService.model.Bill;
import com.dh.usersService.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/all")
  public ResponseEntity<List<Bill>> getAll() {

    return ResponseEntity.ok().body(BillService.getAll());
  }



}
