package com.dh.usersService.service;



import com.dh.usersService.feignInterface.IFeignMsBills;
import com.dh.usersService.model.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    @Autowired
    private IFeignMsBills IFeignMsBills;

//    public String guardar(Bill o){
//        String respuesta = null;
//        if (repository.save(o) != null){
//            respuesta = "ok";
//        }
//        return respuesta;
//    }

    public List<Bill> getAllBill() {

        return (List<Bill>) IFeignMsBills.getAll();
    }

}
