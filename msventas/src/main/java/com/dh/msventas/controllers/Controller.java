package com.dh.msventas.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Controller {
  @GetMapping("/user")
  public String hello() {
    return "hello gateway java 17";
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
}
