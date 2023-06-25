package com.dh.usersService.model;

import lombok.*;

import java.util.List;
import java.util.Map;



public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String nationality;
    private List<Bill> bills;

    public UserDTO(String id, String username, String email, String firstName, String nationality) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.nationality = nationality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}