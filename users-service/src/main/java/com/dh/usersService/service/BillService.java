package com.dh.usersService.service;



import com.dh.usersService.feignInterface.IFeignMsBills;
import com.dh.usersService.model.Bill;
import com.dh.usersService.model.UserDTO;
import com.dh.usersService.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    @Autowired
    private IFeignMsBills IFeignMsBills;

    @Autowired
    private IUserRepository IUserRepository;



    public UserDTO findUserBillsById(String id){
        List<Bill> bills = IFeignMsBills.findByidBill(id).getBody();
        UserDTO user = IUserRepository.findById(id);
        user.setBills(bills);

        return user;
    }




}
