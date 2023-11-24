package com.dh.usersService.repository;


import com.dh.usersService.model.UserDTO;
import com.dh.usersService.model.UserToGrup;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository {

    public List<UserDTO> findAllUsers();

    public List<UserDTO> findByFirName(String name);


    public UserDTO findByEmail(String email);

    public UserDTO findById(String id);

//    public UserBillsDTO findBillsUserById(String id);

    public UserDTO updateNationality(String id, String nationality);

    public UserDTO createUser(UserDTO user);

    public UserToGrup addUserToGroup(UserToGrup data);


}