package com.dh.usersService.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String nationality;
    private List<Bill> bills;

    public User(String id, String username, String email, String firstName, String nationality) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.nationality = nationality;
    }

}
