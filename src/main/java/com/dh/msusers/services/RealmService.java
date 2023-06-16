package com.dh.msusers.services;


import com.dh.msusers.repository.IRealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealmService {
    private IRealmRepository realmRepository;

    //****************************************Constructor***************************************//


    @Autowired
    public RealmService(IRealmRepository realmRepository) {
        this.realmRepository = realmRepository;
    }

    //*******************************************Metodo*******************************************//


    public String createRealm(String realm) {
        // Llama al método createRealm del repositorio
        return realmRepository.createRealm(realm);
    }

    //*****************************************************************************************************//
}
