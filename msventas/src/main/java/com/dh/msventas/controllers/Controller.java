package com.dh.msventas.controllers;

import com.dh.msventas.entity.User;
import com.dh.msventas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hello")
public class Controller {

  @Autowired
  private UserService service;
  @GetMapping("/user")
  public String hello() {
    return "hello gateway con yml";
  }
  @GetMapping("/role")
  @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
  public String withRole() {
    return "Only for User role";
  }

  @GetMapping("/scope")
  @PreAuthorize("hasAnyAuthority('SCOPE_publish')")
  public String withScope() {
    return "Only for Scope Publish";
  }

  @GetMapping("/aud")
  @PreAuthorize("hasAnyAuthority('AUD_account')")
  public String withAud() {
    return "Only for aud scope";
  }

  @GetMapping("/group")
  @PreAuthorize("hasRole('ROLE_admin')")
  public String withGroup() {
    return "Only for Admin group member scope";
  }


  @PostMapping("/crear")
  public ResponseEntity<String> crear(@RequestBody User p) throws Exception{
    ResponseEntity<String> respuesta = null;

    if(service.guardar(p) != null){
      respuesta = ResponseEntity.ok("El Registro fue creado con Exito");
    }else{
      respuesta = ResponseEntity.internalServerError().body("Ooops");
    }

    return respuesta;
  }



}
