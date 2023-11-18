package com.dh.usersService.service;



import com.dh.usersService.model.UserDTO;
import com.dh.usersService.repository.IUserRepository;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository IUserRepository;


    public UserDTO findbyId(String id){
        return IUserRepository.findById(id);

    }

    public UserDTO createUser(UserDTO user) {
        // Realiza cualquier validación o lógica adicional necesaria


        // Llama al método createUser del repositorio
        return IUserRepository.createUser(user);
    }

    public UserDTO findByEmail(String email) {
        return IUserRepository.findByEmail(email);
    }

    public List<UserDTO> findByFirstName(String name) {
        return IUserRepository.findByFirName(name);
    }





}