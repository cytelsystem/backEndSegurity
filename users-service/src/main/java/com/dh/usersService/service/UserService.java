package com.dh.usersService.service;



import com.dh.usersService.model.UserDTO;
import com.dh.usersService.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IUserRepository IUserRepository;


    public UserDTO findbyId(String id){
        return IUserRepository.findById(id);

    }


}