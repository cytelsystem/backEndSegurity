package com.dh.msusers.services;

import com.dh.msusers.modelDTO.UserDTO;
import com.dh.msusers.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private IUserRepository userRepository;

    //***********************Constructor************************//

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //***********************************************************//

    public List<UserDTO> findByFirsName(String firsName){
        return userRepository.findByFirName(firsName);

    }

    public UserDTO findbyId(String id){
        return userRepository.findById(id);

    }

    public UserDTO updateNationality(String id, String nationality){
        return userRepository.updateNationality(id, nationality);
    }






}
