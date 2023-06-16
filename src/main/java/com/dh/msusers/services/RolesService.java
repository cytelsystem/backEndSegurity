package com.dh.msusers.services;


import com.dh.msusers.repository.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    private IRolesRepository rolesRepository;

    //****************************************Constructor***************************************//
    @Autowired
    public RolesService(IRolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    //*******************************************Metodo*******************************************//


    public String createRoles(String roles) {
        // Llama al método createRoles del repositorio
        return rolesRepository.createRoles(roles);
    }

    //*****************************************************************************************************//
}
