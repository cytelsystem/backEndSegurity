package com.dh.userservice.model;

public class User {

    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private SubscriptionDTO subscription;

    public User(Integer id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }
    public User(Integer id, String name, String lastName, String email,SubscriptionDTO subscription) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.subscription = subscription;
    }
    public SubscriptionDTO getSubscriptionDTO() {
        return subscription;
    }

    public void setSubscription(SubscriptionDTO subscription) {
        this.subscription = subscription;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
