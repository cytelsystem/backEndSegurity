package com.dh.usersService.model;

import java.util.List;


public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String nationality;
    private String departamento;
    private List<Bill> bills;

    public UserDTO(String id, String username, String email, String firstName, String nationality) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.nationality = nationality;
        this.departamento = departamento;
        this.bills = bills;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}