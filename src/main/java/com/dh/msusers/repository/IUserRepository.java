package com.dh.msusers.repository;

import com.dh.msusers.modelDTO.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository {

    public List<UserDTO> findAllUsers();

    public List<UserDTO> findByFirName(String name);

    public UserDTO findById(String id);

    public UserDTO updateNationality(String id, String nationality);

    public UserDTO createUser(UserDTO user);


}
