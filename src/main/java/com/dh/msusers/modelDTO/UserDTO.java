package com.dh.msusers.modelDTO;

public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String firtName;
    private String nationality;

    //*********************************Constructor*************************************//

    public UserDTO(String id, String username, String email, String firtName, String nationality) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firtName = firtName;
        this.nationality = nationality;
    }


    //*********************************Getter y Setter*************************************//

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

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    //**************************************************************************************//


}
