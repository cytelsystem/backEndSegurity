package com.dh.usersService.repository;

import com.dh.usersService.feignInterface.IFeignMsBills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class msBillsRepository {

    @Autowired
    private IFeignMsBills IFeignMsBills;


//    public SubscriptionDTO findByUserId(Integer userId){
//        ResponseEntity<SubscriptionDTO> response = IFeignMsBillsRepository.findByUserId(userId);
//        return response.getBody();
//    }




}
